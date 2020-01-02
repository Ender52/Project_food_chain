package cz.cvut.fel.omo.parties.milk;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.product.ProductType;

public class MilkProduction extends Production {

    MilkProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new MilkFarmerStorage();
        myProducts.add(ProductType.MILK);
        myProducts.add(ProductType.MEAT);
    }
}
