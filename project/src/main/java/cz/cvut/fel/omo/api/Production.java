package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.ProductionProcess;

public interface Production {
    int getId();

    ProductType[] getMyProducts();

    ProductionProcess getCurrentProcess();

    Storage getMyStorage();

    Party getOwner();

    void startProducingProducts(ProductType type, int amount);

    void produce();

    int getIdForNewProduct(ProductType productType);
}
