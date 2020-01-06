package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Creation extends Operation {


    public Creation(PartyImpl party, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
    }

    @Override
    public String toString() {
        return "Product " + product.type + " was created by " + party.name + " on " + day + " day";
    }
}
