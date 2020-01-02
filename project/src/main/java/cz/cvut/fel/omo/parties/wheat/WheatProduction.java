package cz.cvut.fel.omo.parties.wheat;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.product.ProductType;

public class WheatProduction extends Production {
    WheatProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new WheatFarmerStorage();
        myProducts.add(ProductType.WHEAT);
    }
}
