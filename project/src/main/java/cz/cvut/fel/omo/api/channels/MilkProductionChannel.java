package cz.cvut.fel.omo.api.channels;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.BlockChainImpl;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class MilkProductionChannel extends ChannelImpl {

    public MilkProductionChannel(BlockChainImpl bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.MILK};

    }
}
