package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.Constants;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Production;
import cz.cvut.fel.omo.api.Storage;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.End;
import cz.cvut.fel.omo.production.ProductionProcess;
import cz.cvut.fel.omo.production.product.Product;

public class ProductionImpl implements Production {
    private Storage myStorage;
    private Party owner;
    private int id = 0;
    private ProductType[] myProducts;
    private ProductionProcess currentProcess;


    public ProductionImpl(Party manufacturer) {
        owner = manufacturer;
        myProducts = owner.getMyProducts();
        myStorage = new StorageImpl(this);
    }


    public int getId() {
        return id;
    }

    public ProductType[] getMyProducts() {
        return myProducts;
    }

    public ProductionProcess getCurrentProcess() {
        return currentProcess;
    }

    public Storage getMyStorage() {
        return myStorage;
    }

    public Party getOwner() {
        return owner;
    }


    public void startProducingProducts(ProductType type, int amount) {
        if (currentProcess != null) return;
        System.out.println("Party " + owner.getName() + " started producing " + type + " in amount " + amount);
        ProductionProcess newProcess = new ProductionProcess(type, amount, this);
        currentProcess = newProcess;
    }


    public void produce() {
        if (currentProcess == null) return;
        currentProcess.timeLapseTick();
        if (currentProcess.getState() instanceof End) {
            Product[] res = currentProcess.getResult();
            for (Product product : res) owner.createOperation("Creation", product);
            for (Product product : res) owner.createOperation("Put", product);
            try {
                myStorage.put(res);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
            currentProcess = null;
        }
    }


    public int getIdForNewProduct(ProductType productType) {
        return Constants.getProductTypeNumber(productType) + owner.getId() * 100000 + id++;
    }

}
