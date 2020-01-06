package cz.cvut.fel.omo.parties.shop;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.Shop;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;
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

    public ShopImpl(String name, EcoSystem ecoSystem, int id) {
        super(name, ecoSystem, id);
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
        try {
            if (!myProduction.myStorage.has(ProductType.BREAD, 5)) createRequest(ProductType.BREAD, 5);
            if (!myProduction.myStorage.has(ProductType.BUN_WITH_ORANGE_JAM, 5))
                createRequest(ProductType.BUN_WITH_ORANGE_JAM, 5);
            if (!myProduction.myStorage.has(ProductType.MILK, 5)) createRequest(ProductType.MILK, 5);
            if (!myProduction.myStorage.has(ProductType.ORANGE, 5)) createRequest(ProductType.ORANGE, 5);
//            if(!myProduction.myStorage.has(ProductType.SAUSAGE,100)) createRequest(ProductType.SAUSAGE, 100);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sellProduct(ProductType p, int amount) {
        int size = 0;
        try {
            size = myProduction.myStorage.size(p);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        if (size < amount) {
            Product[] productsToSell = myProduction.myStorage.takeProducts(p, size);
            for (Product product : productsToSell) createOperation("Take", product);
            wallet.add(productsToSell[0].myPrice.amount * productsToSell.length);
            System.out.println("CUSTOMER BUY " + size + " " + p + "s");
        } else {
            Product[] productsToSell = myProduction.myStorage.takeProducts(p, amount);
            for (Product product : productsToSell) createOperation("Take", product);
            wallet.add(productsToSell[0].myPrice.amount * productsToSell.length);
            System.out.println("CUSTOMER BUY " + amount + " " + p + "s");
        }

    }

    @Override
    public List<ProductType> showAsortiment() {
        List<ProductType> myProducts = new ArrayList<>();
        try {
            if (myProduction.myStorage.has(ProductType.BREAD, 1)) myProducts.add(ProductType.BREAD);
            if (myProduction.myStorage.has(ProductType.BUN_WITH_ORANGE_JAM, 1))
                myProducts.add(ProductType.BUN_WITH_ORANGE_JAM);
            if (myProduction.myStorage.has(ProductType.MILK, 1)) myProducts.add(ProductType.MILK);
            if (myProduction.myStorage.has(ProductType.ORANGE, 1)) myProducts.add(ProductType.ORANGE);
//            if (myProduction.myStorage.has(ProductType.SAUSAGE, 1)) myProducts.add(ProductType.SAUSAGE);
        } catch (WrongProductTypeException e) {
            e.printStackTrace();
        }
        return myProducts;
    }
}
