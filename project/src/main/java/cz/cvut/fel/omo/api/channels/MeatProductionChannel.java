package cz.cvut.fel.omo.api.channels;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.BlockChainImpl;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class MeatProductionChannel extends ChannelImpl {

    public MeatProductionChannel(BlockChainImpl bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.MEAT, ProductType.SAUSAGE};

    }
}