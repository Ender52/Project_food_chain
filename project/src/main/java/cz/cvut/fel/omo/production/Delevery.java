package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Delevery extends Operation {


    public Delevery(Party party, Product[] products, int day) {
        super(party, products, day);
    }

    @Override
    public String toString() {
        return null;
    }
}
