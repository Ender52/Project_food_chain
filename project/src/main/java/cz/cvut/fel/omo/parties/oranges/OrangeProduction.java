package cz.cvut.fel.omo.parties.oranges;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.product.ProductType;

public class OrangeProduction extends Production {

    public OrangeProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new OrangeProductionStorage();
        myProducts.add(ProductType.ORANGE);
    }
}
