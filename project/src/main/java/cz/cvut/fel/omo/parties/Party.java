package cz.cvut.fel.omo.parties;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Creation;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.PutIntoStorage;
import cz.cvut.fel.omo.production.TakenFromStorage;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;
import cz.cvut.fel.omo.transactions.Transaction;

public abstract class Party {
    public final String name;
    protected Production myProduction;
    public BlockChain blockChain;
    public Money wallet;
    protected EcoSystem ecoSystem;
    private int id;

    public Party(String name, EcoSystem ecoSystem, int id) {
        System.out.println("Party " + name + " created");
        this.name = name;
        this.ecoSystem = ecoSystem;
        this.blockChain = ecoSystem.getBlockChain();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void createRequest(ProductType type, int amount);

    public abstract void checkRequestsToMe();

    public void work() {
        if (myProduction != null) myProduction.produce();
    }

    public void startProduceProducts(ProductType type, int amount) {
        myProduction.startProducingProducts(type, amount);
    }

    public void buyProducts(ProductType type, int amount) {
        createRequest(type, amount);

    }

    public void responseToRequest(Request request) {
        System.out.println("Party " + name + " is responding on request from Party " + request.sender.name);
        Product[] products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
        createOperation("Take", products);
        Transaction transaction = new Transaction(this, request.sender, products, ecoSystem.getDay());
        request.channel.doTransaction(transaction);
        request.channel.allRequests.remove(request);
    }

    public void receiveProducts(Product[] products) {
        {
            try {
                createOperation("Put", products);
                myProduction.getMyStorage().put(products);
                System.out.println("Party " + name + " received " + products.length + " " + products[0].type);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeBalance(int amount) {
        System.out.println("Party " + name + " balance chenge " + amount);
        wallet.add(amount);
    }

    public void createOperation(String type, Product[] products) {
        Operation operation;
        if (type.equals("Creation")) {
            operation = new Creation(this, products, ecoSystem.getDay());
        } else if (type.equals("Put")) {
            operation = new PutIntoStorage(this, products, ecoSystem.getDay());
        } else if (type.equals("Take")) {
            operation = new TakenFromStorage(this, products, ecoSystem.getDay());
        } else {
            operation = null;
            System.err.println("Wrong Operation type");
        }
        blockChain.addBlock(operation);
    }

    protected void violateChangeDateOfProduction(Product product) {
        product.getMyOperations().get(0).day = product.getMyOperations().get(0).day + 5;
    }

    protected void violateDoubleSpend(Request request) {
        System.out.println("Party " + name + " is responding on request from Party " + request.sender.name);
        Product[] products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
        createOperation("Take", products);
        Product fake = products[0];
        products[1] = fake;
        Transaction transaction = new Transaction(this, request.sender, products, ecoSystem.getDay());
        request.channel.doTransaction(transaction);
        request.channel.allRequests.remove(request);
    }
}
