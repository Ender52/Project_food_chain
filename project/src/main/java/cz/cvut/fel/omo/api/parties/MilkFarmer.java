package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.Producer;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.api.impl.StorageImpl;
import cz.cvut.fel.omo.transactions.MeatProductionChannel;
import cz.cvut.fel.omo.transactions.MilkProductionChannel;
import cz.cvut.fel.omo.transactions.Money;

/**
 * <p>Party that produces Milks and Meats</p>
 */
public class MilkFarmer extends Producer {
    MilkProductionChannel milkChannel;
    MeatProductionChannel meatChannel;

    /**
     * <p>The constructor</p>
     *
     * @param name The name of given milkFarmer
     * @param id   The unique id of the given milkFarmer
     */
    public MilkFarmer(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.MILK};
        myProduction = new ProductionImpl(this);
        myStorage = new StorageImpl(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.addObserver(this);
        meatChannel = blockChain.meatProductionChannel;
        meatChannel.addObserver(this);
        myChannels.add(milkChannel);
        myChannels.add(meatChannel);
        wallet = new Money(100);
    }
}




