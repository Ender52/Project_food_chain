package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class PutIntoStorage extends Operation {


    public PutIntoStorage(PartyImpl party, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
    }

    @Override
    public String toString() {
        return "Product " + product.type + " was putted into " + party.name + "`s storage on " + day + " day ";

    }
}
