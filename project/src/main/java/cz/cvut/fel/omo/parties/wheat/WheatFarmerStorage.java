package cz.cvut.fel.omo.parties.wheat;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Storage;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public class WheatFarmerStorage implements Storage {
    private List<Product> wheat = new ArrayList<>();
    private WrongProductTypeException exception = new WrongProductTypeException();

    @Override
    public void put(Product[] products) throws WrongProductTypeException {
        for (Product product : products) {
            if (product.type == ProductType.WHEAT) {
                wheat.add(product);
            } else {
                throw exception;
            }
        }
    }

    @Override
    public void put(Product product) throws WrongProductTypeException {
        if (product.type == ProductType.WHEAT) {
            wheat.add(product);
        } else {
            throw exception;
        }
    }

    @Override
    public Product get(ProductType type) {
        if (type == ProductType.WHEAT) return wheat.remove(0);
        else return null;
    }

    @Override
    public Product[] takeProducts(ProductType type, int amount) {
        if (type == ProductType.WHEAT) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = wheat.remove(0);
            }
            return res;
        } else return null;
    }

    @Override
    public boolean has(ProductType type, int amount) throws WrongProductTypeException {
        if (type == ProductType.WHEAT) return wheat.size() >= amount;
        else throw exception;
    }

    @Override
    public int size(ProductType type) throws WrongProductTypeException {
        if (type == ProductType.WHEAT) {
            return wheat.size();
        } else {
            throw exception;

        }
    }
}
