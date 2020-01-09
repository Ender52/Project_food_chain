package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class LogisticChannel extends ChannelImpl {
    public LogisticChannel(BlockChain bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.BREAD, ProductType.BUN_WITH_ORANGE_JAM, ProductType.ORANGE, ProductType.MILK};
    }
}
