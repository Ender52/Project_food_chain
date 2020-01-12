package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.channels.BakeryChannel;
import cz.cvut.fel.omo.api.channels.OrangesChannel;
import cz.cvut.fel.omo.api.impl.Producer;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.api.impl.StorageImpl;
import cz.cvut.fel.omo.transactions.Money;

/**
 * <p>Party that grows Oranges for producing parties</p>
 */
public class OrangeFarmer extends Producer {
    OrangesChannel orangesChannel;
    BakeryChannel bakeryChannel;

    /**
     * <p>The constructor</p>
     *
     * @param name The name of given orangeFarmer
     * @param id   The unique id of the given orangeFarmer
     */
    public OrangeFarmer(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.ORANGE};
        myProduction = new ProductionImpl(this);
        myStorage = new StorageImpl(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.addObserver(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.addObserver(this);
        myChannels.add(orangesChannel);
        myChannels.add(bakeryChannel);
        wallet = new Money(100);
    }
}