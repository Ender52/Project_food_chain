package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class TakenFromStorage extends Operation {


    public TakenFromStorage(Party party, Product[] products, int day) {
        super(party, products, day);
    }

    @Override
    public String toString() {
        return "Product " + products[0].type + " was taken from" + party.name + "`s storage on " + day + " day";

    }
}
