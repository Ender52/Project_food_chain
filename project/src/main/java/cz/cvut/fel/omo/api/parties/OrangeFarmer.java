package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;

public class OrangeFarmer extends PartyImpl {
    OrangesChannel orangesChannel;
    BakeryChannel bakeryChannel;

    public OrangeFarmer(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
        myProducts = new ProductType[]{ProductType.ORANGE};
        myProduction = new ProductionImpl(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.attend(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        myChannels.add(orangesChannel);
        myChannels.add(bakeryChannel);
        wallet = new Money(100);


    }
}