package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class TakenFromStorage extends Operation {


    public TakenFromStorage(PartyImpl party, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
    }

    @Override
    public String toString() {
        return "Product " + product.type + " was taken from" + party.getName() + "`s storage on " + day + " day";

    }
}
