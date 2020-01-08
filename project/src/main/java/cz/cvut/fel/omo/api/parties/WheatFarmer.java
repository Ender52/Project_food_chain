package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;

public class WheatFarmer extends PartyImpl {
    BakeryChannel bakeryChannel;

    public WheatFarmer(String name, EcoSystem e, int id) {
        super(name, e, id);
        myProducts = new ProductType[]{ProductType.WHEAT};
        myProduction = new ProductionImpl(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        myChannels.add(bakeryChannel);
        wallet = new Money(100);
    }
}
