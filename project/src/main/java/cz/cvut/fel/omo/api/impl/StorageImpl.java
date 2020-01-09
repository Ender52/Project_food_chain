package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Storage;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.CookBook;
import cz.cvut.fel.omo.production.product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageImpl implements Storage {
    private WrongProductTypeException exception = new WrongProductTypeException();
    private ArrayList<Product>[] myLists;
    private List<ProductType> myProducts = new ArrayList<>();

    public StorageImpl(Party party) {
        for (ProductType pt : party.getMyProducts()) {
            myProducts.add(pt);
            myProducts.addAll(Arrays.asList(CookBook.getRecipe(pt)));
        }
        myLists = new ArrayList[myProducts.size()];
        for (int i = 0; i < myProducts.size(); i++) myLists[i] = new ArrayList<>();
    }

    @Override
    public List<ProductType> getMyProducts() {
        return myProducts;
    }



    @Override
    public void put(Product[] products) throws WrongProductTypeException {
        for (Product p : products) {
            if (!myProducts.contains(p.type)) throw exception;
            int index = myProducts.indexOf(p.type);
            myLists[index].add(p);
        }
    }

    @Override
    public void put(Product product) throws WrongProductTypeException {
        if (!myProducts.contains(product.type)) throw exception;
        int index = myProducts.indexOf(product.type);
        myLists[index].add(product);
    }

    @Override
    public Product get(ProductType type) throws WrongProductTypeException {
        if (!myProducts.contains(type)) throw exception;
        int index = myProducts.indexOf(type);
        return myLists[index].get(0);
    }

    @Override
    public Product[] takeProducts(ProductType type, int amount) throws WrongProductTypeException {
        Product[] res = new Product[amount];
        if (!myProducts.contains(type)) throw exception;
        int index = myProducts.indexOf(type);
        for (int i = 0; i < amount; i++) res[i] = myLists[index].remove(0);
        return res;
    }

    @Override
    public int size(ProductType type) throws WrongProductTypeException {
        if (!myProducts.contains(type)) throw exception;
        int index = myProducts.indexOf(type);
        return myLists[index].size();
    }

    @Override
    public boolean has(ProductType type, int amount) throws WrongProductTypeException {
        if (!myProducts.contains(type)) throw exception;
        int index = myProducts.indexOf(type);
        return myLists[index].size() >= amount;
    }
}
