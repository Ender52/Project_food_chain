package cz.cvut.fel.omo.parties.oranges;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.ProductType;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;
import cz.cvut.fel.omo.transactions.Request;

public class OrangeFarmer extends PartyImpl {
    OrangesChannel orangesChannel;
    BakeryChannel bakeryChannel;

    public OrangeFarmer(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
        myProduction = new OrangeProduction(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.attend(this);
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
        for (Request r : orangesChannel.allRequests) {
            if (r.productType == ProductType.ORANGE) {
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
//        if (!wasAction) startProduceProducts(ProductType.ORANGE, 50);
    }
}