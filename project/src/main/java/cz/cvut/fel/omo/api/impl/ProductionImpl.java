package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.Constants;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Production;
import cz.cvut.fel.omo.api.product.Product;
import cz.cvut.fel.omo.production.End;
import cz.cvut.fel.omo.production.ProductionProcess;

public class ProductionImpl implements Production {
    private Party owner;
    private int id = 0;
    private ProductType[] myProducts;
    private ProductionProcess currentProcess;


    public ProductionImpl(Party manufacturer) {
        owner = manufacturer;
        myProducts = owner.getMyProducts();
    }


    public int getId() {
        return id;
    }

    public ProductType[] getMyProducts() {
        return myProducts;
    }


    public Party getOwner() {
        return owner;
    }


    public void startProducingProducts(ProductType type, int amount) {
        if (currentProcess != null) return;

        ProductionProcess newProcess = new ProductionProcess(type, amount, this);
        currentProcess = newProcess;
    }


    public Product[] produce() {
        Product[] res = new Product[0];
        if (currentProcess == null) return res;
        currentProcess.timeLapseTick();
        if (currentProcess.getState() instanceof End) {
            res = currentProcess.getResult();
            currentProcess = null;
        }
        return res;
    }


    public int getIdForNewProduct(ProductType productType) {
        return Constants.getProductTypeNumber(productType) + owner.getId() * 100000 + id++;
    }

}
