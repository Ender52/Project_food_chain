import java.util.ArrayList;
import java.util.List;

public abstract class Production {
    Storage myStorage;
    Party owner;
    ArrayList<ProductType> myProducts = new ArrayList<>();
    ArrayList<ProductionProcess> processes = new ArrayList<>();

    Production(Party manufacturer) {
        owner = manufacturer;
    }

    public Storage getMyStorage() {
        return myStorage;
    }

    public Party getOwner() {
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
        processes.removeAll(found);
    }

}
