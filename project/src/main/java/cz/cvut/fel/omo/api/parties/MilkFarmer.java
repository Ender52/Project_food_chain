package cz.cvut.fel.omo.api.parties;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.api.impl.ProductionImpl;
import cz.cvut.fel.omo.transactions.MeatProductionChannel;
import cz.cvut.fel.omo.transactions.MilkProductionChannel;
import cz.cvut.fel.omo.transactions.Money;

public class MilkFarmer extends PartyImpl {
    MilkProductionChannel milkChannel;
    MeatProductionChannel meatChannel;

    public MilkFarmer(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
        myProducts = new ProductType[]{ProductType.MILK};
        myProduction = new ProductionImpl(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.addObserver(this);
        meatChannel = blockChain.meatProductionChannel;
        meatChannel.addObserver(this);
        myChannels.add(milkChannel);
        myChannels.add(meatChannel);
        wallet = new Money(100);
    }

}




