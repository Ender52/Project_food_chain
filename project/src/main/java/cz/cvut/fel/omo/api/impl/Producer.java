package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Production;
import cz.cvut.fel.omo.api.product.Product;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.transactions.Request;

public abstract class Producer extends PartyImpl {
    protected Production myProduction;

    public Producer(String name, int id) {
        super(name, id);
    }

    @Override
    public void work() {
        checkRequestsToMe();

        Product[] res = myProduction.produce();
        if (res.length > 0) {
            for (Product product : res) createOperation("Creation", product);
            for (Product product : res) createOperation("Put", product);
            try {
                myStorage.put(res);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }


    }

    private void startProduceProducts(ProductType type, int amount) {
        myProduction.startProducingProducts(type, amount);
    }

    @Override
    public void checkRequestsToMe() {
        for (Request request : requestsToMe) {
            if (contains(myProducts, request.productType)) {
                try {
                    if (myStorage.has(request.productType, request.amount)) {
                        if (ecoSystem.isReport())
                            System.out.println("Party " + name + " is responding on request from Party " + request.sender.getName());
                        responseToRequest(request);
                    } else {
                        if (ecoSystem.isReport())
                            System.out.println("Party " + name + " is reating on " + request.sender.getName() + "`request and started producing " + request.productType + " in amount " + request.amount);
                        startProduceProducts(request.productType, request.amount);
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
