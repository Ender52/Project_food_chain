package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.Producer;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.api.impl.StorageImpl;
import cz.cvut.fel.omo.transactions.Money;

/**
 * <p>Party that produces Breads and Buns with orange jam from given supplies from farmers</p>
 */
public class Bakery extends Producer {
    /**
     * <p>The constructor</p>
     *
     * @param name The name of given bakery
     * @param id   The unique id of the given bakery
     */
    public Bakery(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.BREAD, ProductType.BUN_WITH_ORANGE_JAM};
        myProduction = new ProductionImpl(this);
        myStorage = new StorageImpl(this);
        blockChain.bakeryChannel.addObserver(this);
        blockChain.orangesChannel.addObserver(this);
        myChannels.add(blockChain.bakeryChannel);
        myChannels.add(blockChain.orangesChannel);
        wallet = new Money(100);
    }
}
