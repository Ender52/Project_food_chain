package cz.cvut.fel.omo.parties.milk;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Storage;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public class MilkFarmerStorage implements Storage {
    private List<Product> milk = new ArrayList<>();
    private List<Product> meat = new ArrayList<>();
    private WrongProductTypeException exception = new WrongProductTypeException();

    @Override
    public void put(Product[] products) throws WrongProductTypeException {
        for (Product product : products) {
            if (product.type == ProductType.MILK) {
                milk.add(product);
            } else if (product.type == ProductType.MEAT) {
                meat.add(product);
            } else {
                throw exception;
            }
        }
    }

    @Override
    public Product get(ProductType type) {
        if (type == ProductType.MILK) return milk.remove(0);
        else if (type == ProductType.MEAT) return meat.remove(0);
        else return null;
    }

    @Override
    public Product[] takeProducts(ProductType type, int amount) {
        if (type == ProductType.MILK) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = milk.remove(0);
            }
            return res;
        } else if (type == ProductType.MEAT) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = meat.remove(0);
            }
            return res;
        } else return null;
    }

    @Override
    public boolean has(ProductType type, int amount) throws WrongProductTypeException {
        if (type == ProductType.MILK) return milk.size() >= amount;
        else if (type == ProductType.MEAT) return meat.size() >= amount;
        else throw exception;
    }
}
