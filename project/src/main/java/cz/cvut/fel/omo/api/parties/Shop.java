package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.channels.LogisticChannel;
import cz.cvut.fel.omo.api.impl.Consumer;
import cz.cvut.fel.omo.api.impl.StorageImpl;
import cz.cvut.fel.omo.transactions.Money;

public class Shop extends Consumer {
    LogisticChannel logisticChannel;
    LogisticChannel shopChannel;

    public Shop(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.ORANGE, ProductType.MILK, ProductType.BUN_WITH_ORANGE_JAM, ProductType.BREAD};
        myStorage = new StorageImpl(this);
        logisticChannel = blockChain.logisticChannel;
        logisticChannel.addObserver(this);
        myChannels.add(logisticChannel);
        shopChannel = blockChain.shopChannel;
        shopChannel.addObserver(this);
        myChannelsToSell.add(shopChannel);
        wallet = new Money(1000000);
    }


}
