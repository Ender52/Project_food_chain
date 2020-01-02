package cz.cvut.fel.omo.parties.wheat;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;

public class WheatFarmer extends Party {
    BakeryChannel bakeryChannel;

    public WheatFarmer(String name, BlockChain blockChain) {
        super(name, blockChain);
        myProduction = new WheatProduction(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.attend(this);
        wallet = new Money(100);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
    }

    @Override
    public void checkRequestsToMe() {
        boolean wasAction = false;
        for (Request r : bakeryChannel.allRequests) {
            if (r.productType == ProductType.WHEAT) {
                try {
                    if (myProduction.getMyStorage().has(ProductType.WHEAT, r.amount)) {
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
        if (!wasAction) startProduceProducts(ProductType.WHEAT, 50);
    }

}
