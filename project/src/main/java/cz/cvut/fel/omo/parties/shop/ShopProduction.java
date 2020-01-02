package cz.cvut.fel.omo.parties.shop;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.Production;

public class ShopProduction extends Production {
    ShopProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new ShopStorage();
    }

    @Override
    public void produce() {

    }
}
