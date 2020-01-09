package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Shop;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.transactions.BakeryChannel;
import cz.cvut.fel.omo.transactions.MilkProductionChannel;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.OrangesChannel;

import java.util.ArrayList;
import java.util.List;

public class ShopImpl extends PartyImpl implements Shop {
    MilkProductionChannel milkChannel;
    BakeryChannel bakeryChannel;
    OrangesChannel orangesChannel;

    public ShopImpl(String name, int id) {
        super(name, id);
        myProducts = new ProductType[]{ProductType.ORANGE, ProductType.MILK, ProductType.BUN_WITH_ORANGE_JAM, ProductType.BREAD};
        myProduction = new ProductionImpl(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.addObserver(this);
        bakeryChannel = blockChain.bakeryChannel;
        bakeryChannel.addObserver(this);
        orangesChannel = blockChain.orangesChannel;
        orangesChannel.addObserver(this);
        myChannels.add(milkChannel);
        myChannels.add(bakeryChannel);
        myChannels.add(orangesChannel);
        wallet = new Money(1000000);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
        if (type == ProductType.MILK) milkChannel.createRequest(type, amount, this);
        else if (type == ProductType.BREAD) bakeryChannel.createRequest(type, amount, this);
        else if (type == ProductType.BUN_WITH_ORANGE_JAM) bakeryChannel.createRequest(type, amount, this);
        else if (type == ProductType.ORANGE) orangesChannel.createRequest(type, amount, this);
        if (ecoSystem.isReport()) System.out.println("Party " + name + " created request " + type + " " + amount);


    }

    @Override
    public void checkRequestsToMe() {
        try {
            if (!myProduction.getMyStorage().has(ProductType.BREAD, 5)) createRequest(ProductType.BREAD, 20);
            if (!myProduction.getMyStorage().has(ProductType.BUN_WITH_ORANGE_JAM, 20))
                createRequest(ProductType.BUN_WITH_ORANGE_JAM, 20);
            if (!myProduction.getMyStorage().has(ProductType.MILK, 5)) createRequest(ProductType.MILK, 20);
            if (!myProduction.getMyStorage().has(ProductType.ORANGE, 5)) createRequest(ProductType.ORANGE, 20);
//            if(!myProduction.myStorage.has(ProductType.SAUSAGE,100)) createRequest(ProductType.SAUSAGE, 100);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sellProduct(ProductType p, int amount) {
        int size = 0;
        try {
            size = myProduction.getMyStorage().size(p);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        if (size < amount) {
            Product[] productsToSell = new Product[0];
            try {
                productsToSell = myProduction.getMyStorage().takeProducts(p, size);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
            for (Product product : productsToSell) createOperation("Take", product);
            wallet.add(productsToSell[0].myPrice.amount * productsToSell.length);
            if (ecoSystem.isReport()) System.out.println("CUSTOMER BUY " + size + " " + p + "s");
        } else {
            Product[] productsToSell = new Product[0];
            try {
                productsToSell = myProduction.getMyStorage().takeProducts(p, amount);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
            for (Product product : productsToSell) createOperation("Take", product);
            wallet.add(productsToSell[0].myPrice.amount * productsToSell.length);
            System.out.println("CUSTOMER BUY " + amount + " " + p + "s");
        }

    }

    @Override
    public List<ProductType> showAsortiment() {
        List<ProductType> myProducts = new ArrayList<>();
        try {
            if (myProduction.getMyStorage().has(ProductType.BREAD, 1)) myProducts.add(ProductType.BREAD);
            if (myProduction.getMyStorage().has(ProductType.BUN_WITH_ORANGE_JAM, 1))
                myProducts.add(ProductType.BUN_WITH_ORANGE_JAM);
            if (myProduction.getMyStorage().has(ProductType.MILK, 1)) myProducts.add(ProductType.MILK);
            if (myProduction.getMyStorage().has(ProductType.ORANGE, 1)) myProducts.add(ProductType.ORANGE);
//            if (myProduction.myStorage.has(ProductType.SAUSAGE, 1)) myProducts.add(ProductType.SAUSAGE);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        return myProducts;
    }
}
