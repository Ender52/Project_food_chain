package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.channels.BakeryChannel;
import cz.cvut.fel.omo.api.channels.LogisticChannel;
import cz.cvut.fel.omo.api.channels.MilkProductionChannel;
import cz.cvut.fel.omo.api.channels.OrangesChannel;
import cz.cvut.fel.omo.api.impl.Consumer;
import cz.cvut.fel.omo.api.impl.StorageImpl;
import cz.cvut.fel.omo.transactions.Money;

public class Distributor extends Consumer {
    MilkProductionChannel milkChannel;
    BakeryChannel bakeryChannel;
    OrangesChannel orangesChannel;
    LogisticChannel logisticChannel;

    public Distributor(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.ORANGE, ProductType.MILK, ProductType.BUN_WITH_ORANGE_JAM, ProductType.BREAD};
        myStorage = new StorageImpl(this);
        logisticChannel = blockChain.logisticChannel;
        logisticChannel.addObserver(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.addObserver(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.addObserver(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.addObserver(this);
        myChannelsToSell.add(logisticChannel);
        myChannels.add(milkChannel);
        myChannels.add(bakeryChannel);
        myChannels.add(orangesChannel);
        wallet = new Money(1000000);
    }

}
