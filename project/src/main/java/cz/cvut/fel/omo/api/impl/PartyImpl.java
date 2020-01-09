package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.*;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Creation;
import cz.cvut.fel.omo.production.PutIntoStorage;
import cz.cvut.fel.omo.production.TakenFromStorage;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;
import cz.cvut.fel.omo.transactions.Transaction;
import cz.cvut.fel.omo.transactions.TransactionForReport;

import java.util.ArrayList;
import java.util.List;

public abstract class PartyImpl implements Party, Observer, OperationFactory {
    protected final String name;
    protected Production myProduction;
    protected BlockChain blockChain;
    protected Money wallet;
    protected EcoSystem ecoSystem;
    protected ProductType[] myProducts;
    protected List<Channel> myChannels = new ArrayList<>();
    protected int id;
    protected List<Request> requestsToMe = new ArrayList<>();

    public PartyImpl(String name, int id) {
        System.out.println("Party " + name + " created");
        this.name = name;
        this.ecoSystem = EcoSystem.getInstance();
        this.blockChain = ecoSystem.getBlockChain();
        this.id = id;
        myProducts = new ProductType[0];
    }


    @Override
    public List<Channel> getMyChannels() {
        return myChannels;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Production getMyProduction() {
        return myProduction;
    }

    @Override
    public BlockChain getBlockChain() {
        return blockChain;
    }

    @Override
    public Money getWallet() {
        return wallet;
    }

    @Override
    public EcoSystem getEcoSystem() {
        return ecoSystem;
    }

    @Override
    public ProductType[] getMyProducts() {
        return myProducts;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public void createRequest(ProductType type, int amount) {
        myChannels.forEach(channel -> {
            if (contains(channel.getMyProducts(), type)) {
                if (ecoSystem.isReport())
                    System.out.println("Party " + name + " created request: " + type + " int amount " + amount);
                channel.createRequest(type, amount, this);
            }
        });
    }

    @Override
    public void checkRequestsToMe() {
        for (Request request : requestsToMe) {
            if (contains(myProducts, request.productType)) {
                try {
                    if (myProduction.getMyStorage().has(request.productType, request.amount)) {
                        if (ecoSystem.isReport())
                            System.out.println("Party " + name + " is responding on request from Party " + request.sender.getName());
                        responseToRequest(request);
                    } else {
                        if (ecoSystem.isReport())
                            System.out.println("Party " + name + " is reating on " + request.sender.getName() + "`request and started producing " + request.productType + " in amount " + request.amount);
                        startProduceProducts(request.productType, request.amount);
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private boolean contains(ProductType[] array, ProductType type) {
        for (ProductType productType : array) {
            if (productType == type) return true;
        }
        return false;
    }


    @Override
    public void work() {
        if (myProduction != null) {
            Product[] res = myProduction.produce();
            if (res.length > 0) {
                for (Product product : res) createOperation("Creation", product);
                for (Product product : res) createOperation("Put", product);
                try {
                    myProduction.getMyStorage().put(res);
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void startProduceProducts(ProductType type, int amount) {
        myProduction.startProducingProducts(type, amount);
    }

    @Override
    public void buyProducts(ProductType type, int amount) {
        createRequest(type, amount);
    }

    @Override
    public void responseToRequest(Request request) {
        int randomToDoubleSpend = (int) (Math.random() * 100);

        if (randomToDoubleSpend < 10 && request.amount >= 2) {
            violateDoubleSpend(request);
        } else {
//            System.out.println("Party " + name + " is responding on request from Party " + request.sender.getName());
            doTransaction(request);
        }
        requestsToMe.remove(request);
        request.channel.deleteRequest(request);

    }

    @Override
    public void receiveProduct(Product product) {
        {
            try {
                myProduction.getMyStorage().put(product);
                createOperation("Put", product);
                if (ecoSystem.isReport()) System.out.println("Party " + name + " received " + product.type);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeBalance(int amount) {
        wallet.add(amount);
        if (ecoSystem.isReport())
            System.out.println("Party " + name + " balance chenge " + amount + " Current balance: " + wallet.amount);

    }

    @Override
    public Transaction createTransaction(Party receiver, Product product) {
        String prevHash = blockChain.getChain().size() == 0 ? "" : blockChain.getChain().get(blockChain.getChain().size() - 1).getMyHash();
        Transaction transaction = new Transaction(this, receiver, product, ecoSystem.getDay(), prevHash);
        blockChain.addBlock(transaction);
        return transaction;
    }

    @Override
    public Operation createOperation(String type, Product product) {
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
        return operation;
    }

    private void violateChangeDateOfProduction(Product product) {
        Operation creation = blockChain.getChain().stream().filter(operation -> operation.product.getId() == product.getId()).findFirst().get();
        Operation fake = new Creation(creation.party, creation.product, creation.day + 2, creation.getPrevBlockHash());
        int index = blockChain.getChain().indexOf(creation);
        blockChain.getChain().set(index, fake);
    }

    private void violateDoubleSpend(Request request) {
        Product[] products = new Product[0];
        Party reseiver = request.sender;
        try {
            products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        for (Product p : products) createOperation("Take", p);
        for (Product p : products) {
            int randomToChangeBlock = (int) (Math.random() * 100);
            if (randomToChangeBlock < 2) violateChangeDateOfProduction(p);
        }
        int i = 0;
        for (Product p : products) {
            if (i == 1) p = products[0];
            reseiver.receiveProduct(p);
            createTransaction(reseiver, p);
            i++;
        }
        blockChain.addTransactionForReport(new TransactionForReport(this, request.sender, request.productType, request.amount, ecoSystem.getDay()));

    }


    @Override
    public void doTransaction(Request request) {
        Product[] products = new Product[0];
        Party reseiver = request.sender;
        try {
            products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
            if (ecoSystem.isReport())
                System.out.println("Party " + name + " takes " + request.amount + " " + request.productType + " from storage");
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        for (Product p : products) createOperation("Take", p);
        for (Product p : products) {
            int randomToChangeBlock = (int) (Math.random() * 100);
            if (randomToChangeBlock < 2) violateChangeDateOfProduction(p);
        }
        for (Product p : products) {
            reseiver.receiveProduct(p);
            createTransaction(reseiver, p);
        }
        blockChain.addTransactionForReport(new TransactionForReport(this, request.sender, request.productType, request.amount, ecoSystem.getDay()));


    }


    @Override
    public void update(Request request, boolean added) {
        if (added) requestsToMe.add(request);
        else requestsToMe.remove(request);
    }


    @Override
    public void createOp(String type, Product product) {
        createOperation(type, product);
    }
}
