package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.ProductType;

public class Request {

    public final ProductType productType;
    public final PartyImpl sender;
    public final int amount;
    public final AbstractChannel channel;

    public Request(ProductType pType, PartyImpl sender, int amount, AbstractChannel channel) {
        productType = pType;
        this.sender = sender;
        this.amount = amount;
        this.channel = channel;
    }
}
