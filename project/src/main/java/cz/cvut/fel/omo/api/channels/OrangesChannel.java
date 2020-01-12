package cz.cvut.fel.omo.api.channels;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.BlockChainImpl;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class OrangesChannel extends ChannelImpl {

    public OrangesChannel(BlockChainImpl bc) {
        super(bc);
        myProducts = new ProductType[]{ProductType.ORANGE};
    }
}
