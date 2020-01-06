package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.Constants;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public abstract class Production {
    public Storage myStorage;
    public PartyImpl owner;
    private int id = 0;
    public ArrayList<ProductType> myProducts = new ArrayList<>();
    public ArrayList<ProductionProcess> processes = new ArrayList<>();

    public Production(PartyImpl manufacturer) {
        owner = manufacturer;
    }

    public Storage getMyStorage() {
        return myStorage;
    }

    public PartyImpl getOwner() {
        return owner;
    }

    public ArrayList<ProductType> getMyProducts() {
        return myProducts;
    }

    public ArrayList<ProductionProcess> getProcesses() {
        return processes;
    }

    public void startProducingProducts(ProductType type, int amount) {
        if (!myProducts.contains(type) || alreadyProducing(type)) return;
        System.out.println("Party " + owner.name + " started produsing " + type + " in amount " + amount);

        ProductionProcess newProcess = new ProductionProcess(type, amount, this);
        processes.add(newProcess);

    }

    private boolean alreadyProducing(ProductType type) {
        for (ProductionProcess pp : processes) {
            if (pp.getMyType() == type) return true;
        }
        return false;
    }

    public void produce() {
        List<ProductionProcess> found = new ArrayList<>();
        for (ProductionProcess pp : processes) {
            pp.timeLapseTick();
            if (pp.getState() instanceof End) found.add(pp);

        }
        for (ProductionProcess p : found) {
            Product[] res = p.getResult();
            for (Product product : res) owner.createOperation("Creation", product);
            for (Product product : res) owner.createOperation("Put", product);
            try {
                myStorage.put(p.getResult());
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
        processes.removeAll(found);
    }


    public int getIdForNewProduct(ProductType productType) {
        return Constants.getProductTypeNumber(productType) + id++;
    }

}
