package cz.cvut.fel.omo.parties.shop;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.Production;

public class ShopProduction extends Production {
    ShopProduction(PartyImpl manufacturer) {
        super(manufacturer);
        myStorage = new ShopStorage();
    }

    @Override
    public void produce() {

    }
}
