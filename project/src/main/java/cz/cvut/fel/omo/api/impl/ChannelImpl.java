package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.Channel;
import cz.cvut.fel.omo.api.Observer;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Subject;
import cz.cvut.fel.omo.transactions.Request;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class ChannelImpl implements Channel, Subject {
    protected Pair<Request, Boolean> lastChange;
    protected BlockChain blockChain;
    protected ArrayList<Observer> participants = new ArrayList<>();
    protected ProductType[] myProducts;


    public ChannelImpl(BlockChain bc) {
        blockChain = bc;
    }


    public BlockChain getBlockChain() {
        return blockChain;
    }



    public ProductType[] getMyProducts() {
        return myProducts;
    }


    public void createRequest(Request request) {
        if (!contains(myProducts, request.productType)) return;
        lastChange = new Pair<>(request, true);
        notifyAllObservers();
    }

    protected boolean contains(ProductType[] array, ProductType type) {
        for (ProductType productType : array) {
            if (productType == type) return true;
        }
        return false;
    }
    @Override
    public void deleteRequest(Request request) {
        lastChange = new Pair<>(request, false);
        notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : participants) {
            observer.update(lastChange.getKey(), lastChange.getValue());
        }
    }

    @Override
    public void addObserver(Observer observer) {
        participants.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        participants.remove(observer);
    }

}
