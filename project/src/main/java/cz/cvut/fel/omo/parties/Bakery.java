package cz.cvut.fel.omo.parties;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;

public class Bakery extends PartyImpl {
    BakeryChannel bakeryChannel;
    OrangesChannel orangesChannel;

    public Bakery(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
        myProducts = new ProductType[]{ProductType.BREAD, ProductType.BUN_WITH_ORANGE_JAM};
        myProduction = new ProductionImpl(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.attend(this);
        myChannels.add(bakeryChannel);
        myChannels.add(orangesChannel);
        wallet = new Money(100);

    }



}
