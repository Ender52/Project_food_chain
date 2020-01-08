package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.Money;

public class Bakery extends PartyImpl {

    public Bakery(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
        myProducts = new ProductType[]{ProductType.BREAD, ProductType.BUN_WITH_ORANGE_JAM};
        myProduction = new ProductionImpl(this);
        blockChain.bakeryChannel.attend(this);
        blockChain.orangesChannel.attend(this);
        myChannels.add(blockChain.bakeryChannel);
        myChannels.add(blockChain.orangesChannel);
        wallet = new Money(100);

    }



}
