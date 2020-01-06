package cz.cvut.fel.omo.parties.milk;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.product.ProductType;

public class MilkProduction extends Production {

    MilkProduction(PartyImpl manufacturer) {
        super(manufacturer);
        myStorage = new MilkFarmerStorage();
        myProducts.add(ProductType.MILK);
        myProducts.add(ProductType.MEAT);
    }
}
