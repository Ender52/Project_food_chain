package cz.cvut.fel.omo.parties.milk;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.MeatProductionChannel;
import cz.cvut.fel.omo.transactions.MilkProductionChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;

public class MilkFarmer extends Party {
    MilkProductionChannel milkChannel;
    MeatProductionChannel meatChannel;

    public MilkFarmer(String name, BlockChain blockChain) {
        super(name, blockChain);
        myProduction = new MilkProduction(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.attend(this);
        meatChannel = blockChain.meatProductionChannel;
        meatChannel.attend(this);
        wallet = new Money(100);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
    }

    @Override
    public void checkRequestsToMe() {
        boolean wasAction = false;
        for (Request r : milkChannel.allRequests) {
            if (r.productType == ProductType.MILK) {
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
        for (Request r : meatChannel.allRequests) {
            if (r.productType == ProductType.MEAT) {
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
        if (!wasAction) startProduceProducts(ProductType.MILK, 50);
    }
}




