package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.*;
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

    public void createRequest(ProductType type, int amount, Party sender) {
        lastChange = new Pair<>(new Request(type, sender, amount, this), true);
        notifyAllObservers();
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
