package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;

/**
 * <p>Party that grows Wheats for producing parties</p>
 */
public class WheatFarmer extends PartyImpl {
    BakeryChannel bakeryChannel;

    /**
     * <p>The constructor</p>
     *
     * @param name The name of given wheatFarmer
     * @param id   The unique id of the given wheatFarmer
     */
    public WheatFarmer(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.WHEAT};
        myProduction = new ProductionImpl(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.addObserver(this);
        myChannels.add(bakeryChannel);
        wallet = new Money(100);
    }
}
