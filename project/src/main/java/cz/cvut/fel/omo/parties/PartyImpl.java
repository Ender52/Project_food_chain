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

public abstract class PartyImpl {
    public final String name;
    public Production myProduction;
    public BlockChain blockChain;
    public Money wallet;
    protected EcoSystem ecoSystem;
    private ProductType[] myProducts;
    private int id;

    public PartyImpl(String name, EcoSystem ecoSystem, int id) {
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
        int randomToDoubleSpend = (int) (Math.random() * 100);
        int randomToChangeBlock = (int) (Math.random() * 100);
        if (randomToDoubleSpend < 10 && request.amount >= 2) {
            violateDoubleSpend(request);
        } else {
            System.out.println("Party " + name + " is responding on request from Party " + request.sender.name);
            Product[] products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
            for (Product p : products) createOperation("Take", p);
            for (Product p : products) {
                if (randomToChangeBlock < 10) violateChangeDateOfProduction(p);
                request.channel.doTransaction(createTransaction(request.sender, p));
            }
            request.channel.allRequests.remove(request);
        }

    }

    public void receiveProduct(Product product) {
        {
            try {
                createOperation("Put", product);
                myProduction.getMyStorage().put(product);
                System.out.println("Party " + name + " received " + product.type);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeBalance(int amount) {
        wallet.add(amount);
        System.out.println("Party " + name + " balance chenge " + amount + " Current balance: " + wallet.amount);

    }

    public Transaction createTransaction(PartyImpl receiver, Product product) {
        String prevHash = blockChain.getChain().size() == 0 ? "" : blockChain.getChain().get(blockChain.getChain().size() - 1).getMyHash();
        Transaction transaction = new Transaction(this, receiver, product, ecoSystem.getDay(), prevHash);
        blockChain.addBlock(transaction);
        return transaction;
    }

    public void createOperation(String type, Product product) {
        Operation operation;
        String prevHash = blockChain.getChain().size() == 0 ? "" : blockChain.getChain().get(blockChain.getChain().size() - 1).getMyHash();
        if (type.equals("Creation")) {
            operation = new Creation(this, product, ecoSystem.getDay(), prevHash);
        } else if (type.equals("Put")) {
            operation = new PutIntoStorage(this, product, ecoSystem.getDay(), prevHash);
        } else if (type.equals("Take")) {
            operation = new TakenFromStorage(this, product, ecoSystem.getDay(), prevHash);
        } else {
            operation = null;
            System.err.println("Wrong Operation type");
        }
        blockChain.addBlock(operation);
    }

    private void violateChangeDateOfProduction(Product product) {
        Operation creation = blockChain.getChain().stream().filter(operation -> operation.product.getId() == product.getId()).findFirst().get();
        Operation fake = new Creation(creation.party, creation.product, creation.day + 2, creation.getPrevBlockHash());
        int index = blockChain.getChain().indexOf(creation);
        blockChain.getChain().set(index, fake);
    }

    private void violateDoubleSpend(Request request) {
        System.out.println("Party " + name + " is responding on request from Party " + request.sender.name);
        Product[] products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
        for (Product p : products) createOperation("Take", p);
        int i = 0;
        String prevHash = blockChain.getChain().size() == 0 ? "" : blockChain.getChain().get(blockChain.getChain().size() - 1).getMyHash();
        for (Product p : products) {
            if (i == 1) p = products[0];
            request.channel.doTransaction(createTransaction(request.sender, p));
            i++;
        }
        request.channel.allRequests.remove(request);
    }
}
