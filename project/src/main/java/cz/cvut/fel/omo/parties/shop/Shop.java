package cz.cvut.fel.omo.parties.shop;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.MilkProductionChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;

public class Shop extends Party {
    MilkProductionChannel milkChannel;
    BakeryChannel bakeryChannel;
    OrangesChannel orangesChannel;

    public Shop(String name, EcoSystem ecoSystem) {
        super(name, ecoSystem);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.attend(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.attend(this);
        myProduction = new ShopProduction(this);
        wallet = new Money(1000000);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
        if (type == ProductType.MILK) milkChannel.createRequest(type, amount, this);
        else if (type == ProductType.BREAD) bakeryChannel.createRequest(type, amount, this);
        else if (type == ProductType.BUN_WITH_ORANGE_JAM) bakeryChannel.createRequest(type, amount, this);
        else if (type == ProductType.ORANGE) orangesChannel.createRequest(type, amount, this);
        System.out.println("Party " + name + " created request " + type + " " + amount);


    }

    @Override
    public void checkRequestsToMe() {
        int x = (int) (Math.random() * (5));
        switch (x) {
            case 0:
                createRequest(ProductType.MILK, 200);
                break;
            case 1:
                createRequest(ProductType.BREAD, 250);
                break;
            case 2:
                createRequest(ProductType.BUN_WITH_ORANGE_JAM, 250);
                break;
            case 3:
                createRequest(ProductType.BUN_WITH_ORANGE_JAM, 250);
                break;
            case 4:
                createRequest(ProductType.ORANGE, 250);
                break;
        }

    }
}
