package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.ProductionProcess;
import cz.cvut.fel.omo.production.product.Product;

public interface Production {
    int getId();

    ProductType[] getMyProducts();

    ProductionProcess getCurrentProcess();

    Storage getMyStorage();

    Party getOwner();

    void startProducingProducts(ProductType type, int amount);

    Product[] produce();

    int getIdForNewProduct(ProductType productType);
}
