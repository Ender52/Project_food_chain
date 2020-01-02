package cz.cvut.fel.omo.parties.bakery;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.Production;
import cz.cvut.fel.omo.production.ProductionProcess;
import cz.cvut.fel.omo.production.product.ProductType;

public class BakeryProduction extends Production {

    public BakeryProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new BakeryProductionStorage();
        myProducts.add(ProductType.BREAD);
        myProducts.add(ProductType.BUN_WITH_ORANGE_JAM);
    }

    @Override
    public void startProducingProducts(ProductType type, int amount) {
        if (!myProducts.contains(type) || alreadyProducing(type)) return;
        System.out.println("Party " + owner.name + " started produsing " + type + " in amount " + amount);

        ProductionProcess newProcess = new ProductionProcess(type, amount, this);
        processes.add(newProcess);

    }

    private boolean alreadyProducing(ProductType type) {
        for (ProductionProcess pp : processes) {
            if (pp.getMyType() == type || (type == ProductType.BREAD && pp.getMyType() == ProductType.BUN_WITH_ORANGE_JAM) || (type == ProductType.BUN_WITH_ORANGE_JAM && pp.getMyType() == ProductType.BREAD))
                return true;
        }
        return false;
    }
}
