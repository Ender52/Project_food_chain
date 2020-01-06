package cz.cvut.fel.omo.parties.oranges;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.product.ProductType;

public class OrangeProduction extends Production {

    public OrangeProduction(PartyImpl manufacturer) {
        super(manufacturer);
        myStorage = new OrangeProductionStorage();
        myProducts.add(ProductType.ORANGE);
    }
}
