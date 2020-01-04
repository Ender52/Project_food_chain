package cz.cvut.fel.omo.parties.bakery;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;
import cz.cvut.fel.omo.transactions.Request;

public class Bakery extends Party {
    BakeryChannel bakeryChannel;
    OrangesChannel orangesChannel;

    public Bakery(String name, EcoSystem ecoSystem) {
        super(name, ecoSystem);
        myProduction = new BakeryProduction(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.attend(this);
        wallet = new Money(100);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
        if (type == ProductType.WHEAT) bakeryChannel.createRequest(type, amount, this);
        else if (type == ProductType.ORANGE) orangesChannel.createRequest(type, amount, this);
        System.out.println("Party " + name + " created request " + type + " " + amount);

    }

    @Override
    public void checkRequestsToMe() {
        boolean wasAction = false;
        for (Request r : bakeryChannel.allRequests) {
            if (r.productType == ProductType.BREAD || r.productType == ProductType.BUN_WITH_ORANGE_JAM) {
                try {
                    if (myProduction.getMyStorage().has(r.productType, r.amount)) {
                        responseToRequest(r);
                        wasAction = true;
                        break;
                    } else {
                        startProduceProducts(r.productType, r.amount);
                        wasAction = true;
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!wasAction) startProduceProducts(ProductType.BREAD, 50);
    }
}
