package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.transactions.LogisticChannel;
import cz.cvut.fel.omo.transactions.Money;

public class Customer extends PartyImpl {

    public Customer(String name, int id) {
        super(name, id);
        LogisticChannel shopChannel = blockChain.shopChannel;
        shopChannel.addObserver(this);
        myChannels.add(shopChannel);
        myProducts = shopChannel.myProducts;
        myStorage = new StorageImpl(this);
        wallet = new Money(1000);
    }

    @Override
    public void checkRequestsToMe() {

    }

    @Override
    public void work() {
        int random = (int) (Math.random() * myProducts.length);
        buyProducts(myProducts[random], random + 1);
        wallet.add(20);
    }
}
