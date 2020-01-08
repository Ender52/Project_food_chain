package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.ChannelImpl;

public class Request {

    public final ProductType productType;
    public final Party sender;
    public final int amount;
    public final ChannelImpl channel;

    public Request(ProductType pType, Party sender, int amount, ChannelImpl channel) {
        productType = pType;
        this.sender = sender;
        this.amount = amount;
        this.channel = channel;
    }
}
