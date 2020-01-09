package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.Channel;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.transactions.Request;

import java.util.ArrayList;
import java.util.List;

public abstract class Consumer extends PartyImpl {
    protected List<Channel> myChannelsToSell = new ArrayList<>();

    public Consumer(java.lang.String name, int id) {
        super(name, id);
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
                        buyProducts(request.productType, request.amount);
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void work() {
        checkRequestsToMe();
    }

    @Override
    public void update(Request request, boolean added) {
        if (myChannelsToSell.contains(request.channel)) {
            if (added) requestsToMe.add(request);
            else requestsToMe.remove(request);
        }
    }


}
