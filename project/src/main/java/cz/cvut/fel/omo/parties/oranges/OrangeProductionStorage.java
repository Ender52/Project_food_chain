package cz.cvut.fel.omo.parties.oranges;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Storage;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public class OrangeProductionStorage implements Storage {
    private List<Product> oranges = new ArrayList<>();
    private WrongProductTypeException exception = new WrongProductTypeException();

    @Override
    public void put(Product[] products) throws WrongProductTypeException {
        for (Product product : products) {
            if (product.type == ProductType.ORANGE) {
                oranges.add(product);
            } else {
                throw exception;
            }
        }
    }

    @Override
    public Product get(ProductType type) {
        if (type == ProductType.ORANGE) return oranges.remove(0);
        else return null;
    }

    @Override
    public Product[] takeProducts(ProductType type, int amount) {
        if (type == ProductType.ORANGE) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = oranges.remove(0);
            }
            return res;
        } else return null;
    }

    @Override
    public boolean has(ProductType type, int amount) throws WrongProductTypeException {
        if (type == ProductType.ORANGE) return oranges.size() >= amount;
        else throw exception;
    }
}