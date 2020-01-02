package cz.cvut.fel.omo.parties;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Production;
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


    public Party(String name, BlockChain bc) {
        System.out.println("Party " + name + " created");
        this.name = name;
        this.blockChain = bc;
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
        Product[] products = myProduction.getMyStorage().getProducts(request.productType, request.amount);
        Transaction transaction = new Transaction(this, request.sender, products);
        request.channel.doTransaction(transaction);
        request.channel.allRequests.remove(request);
    }

    public void receiveProducts(Product[] products) {
        {
            try {
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
}
