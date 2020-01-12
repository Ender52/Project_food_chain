package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.api.Channel;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;

public class Request {

    public final ProductType productType;
    public final Party sender;
    public final int amount;
    public final Channel channel;

    public Request(ProductType pType, Party sender, int amount, Channel channel) {
        productType = pType;
        this.sender = sender;
        this.amount = amount;
        this.channel = channel;
    }
}
