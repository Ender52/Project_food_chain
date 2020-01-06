package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Creation extends Operation {


    public Creation(Party party, Product[] products, int day) {
        super(party, products, day);
    }

    @Override
    public String toString() {
        return "Product " + products[0].type + " was created by " + party.name + " on " + day + " day";
    }
}
