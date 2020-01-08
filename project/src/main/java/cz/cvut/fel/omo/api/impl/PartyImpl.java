package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.Channel;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Production;
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

public abstract class PartyImpl implements Party {
    protected final String name;
    protected Production myProduction;
    protected BlockChain blockChain;
    protected Money wallet;
    protected EcoSystem ecoSystem;
    protected ProductType[] myProducts;
    protected List<Channel> myChannels = new ArrayList<>();
    protected int id;


    public PartyImpl(String name, EcoSystem ecoSystem, int id) {
        System.out.println("Party " + name + " created");
        this.name = name;
        this.ecoSystem = ecoSystem;
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
            if (contains(channel.getMyProducts(), type)) channel.createRequest(type, amount, this);
        });
    }

    @Override
    public void checkRequestsToMe() {
        myChannels.forEach(channel -> {
            for (Request request : channel.getAllRequests()) {
                if (contains(myProducts, request.productType)) {
                    try {
                        if (myProduction.getMyStorage().has(request.productType, request.amount)) {
                            responseToRequest(request);
                        } else {
                            startProduceProducts(request.productType, request.amount);
                        }
                    } catch (WrongProductTypeException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        });
    }

    private boolean contains(ProductType[] array, ProductType type) {
        for (ProductType productType : array) {
            if (productType == type) return true;
        }
        return false;
    }


    @Override
    public void work() {
        if (myProduction != null) myProduction.produce();
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
            System.out.println("Party " + name + " is responding on request from Party " + request.sender.getName());
            Product[] products = new Product[0];
            try {
                products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
            for (Product p : products) createOperation("Take", p);
            for (Product p : products) {
                int randomToChangeBlock = (int) (Math.random() * 100);
                if (randomToChangeBlock < 2) violateChangeDateOfProduction(p);
                request.channel.doTransaction(createTransaction(request.sender, p));
            }
            blockChain.addTransactionForReport(new TransactionForReport(this, request.sender, request.productType, request.amount, ecoSystem.getDay()));
            request.channel.getAllRequests().remove(request);
        }

    }

    @Override
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

    @Override
    public void changeBalance(int amount) {
        wallet.add(amount);
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
        System.out.println("Party " + name + " is responding on request from Party " + request.sender.getName());
        Product[] products = new Product[0];
        try {
            products = myProduction.getMyStorage().takeProducts(request.productType, request.amount);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        for (Product p : products) createOperation("Take", p);
        int i = 0;
        String prevHash = blockChain.getChain().size() == 0 ? "" : blockChain.getChain().get(blockChain.getChain().size() - 1).getMyHash();
        for (Product p : products) {
            if (i == 1) p = products[0];
            request.channel.doTransaction(createTransaction(request.sender, p));
            i++;
        }
        request.channel.getAllRequests().remove(request);
    }
}