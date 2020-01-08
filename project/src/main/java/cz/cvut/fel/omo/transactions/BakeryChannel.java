package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class BakeryChannel extends ChannelImpl {

    public BakeryChannel(BlockChain bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.BUN_WITH_ORANGE_JAM, ProductType.BUN_WITH_ORANGE_JAM, ProductType.WHEAT};
    }
}
