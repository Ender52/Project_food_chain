package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class MilkProductionChannel extends ChannelImpl {

    public MilkProductionChannel(BlockChain bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.MILK};

    }
}
