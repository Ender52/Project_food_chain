package cz.cvut.fel.omo.parties.shop;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.Storage;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ShopStorage implements Storage {
    private List<Product> milk = new ArrayList<>();
    private List<Product> bunWithJam = new ArrayList<>();
    private List<Product> bread = new ArrayList<>();
    private List<Product> sausage = new ArrayList<>();
    private List<Product> oranges = new ArrayList<>();
    private WrongProductTypeException exception = new WrongProductTypeException();

    @Override
    public void put(Product[] products) throws WrongProductTypeException {
        for (Product product : products) {
            if (product.type == ProductType.BREAD) {
                bread.add(product);
            } else if (product.type == ProductType.BUN_WITH_ORANGE_JAM) {
                bunWithJam.add(product);
            } else if (product.type == ProductType.MILK) {
                milk.add(product);
            } else if (product.type == ProductType.ORANGE) {
                oranges.add(product);
            } else {
                throw exception;
            }
        }
    }

    @Override
    public Product get(ProductType type) {
        if (type == ProductType.BREAD) {
            return bread.remove(0);
        } else if (type == ProductType.BUN_WITH_ORANGE_JAM) {
            return bunWithJam.remove(0);
        } else if (type == ProductType.MILK) {
            return milk.remove(0);
        } else if (type == ProductType.ORANGE) {
            return oranges.remove(0);
        } else {
            return null;
        }
    }

    @Override
    public Product[] takeProducts(ProductType type, int amount) {
        if (type == ProductType.BREAD) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = bread.remove(0);
            }
            return res;
        } else if (type == ProductType.BUN_WITH_ORANGE_JAM) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = bunWithJam.remove(0);
            }
            return res;
        } else if (type == ProductType.MILK) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = milk.remove(0);
            }
            return res;
        } else if (type == ProductType.ORANGE) {
            Product[] res = new Product[amount];
            for (int i = 0; i < amount; i++) {
                res[i] = oranges.remove(0);
            }
            return res;
        } else {
            return null;
        }
    }

    @Override
    public boolean has(ProductType type, int amount) throws WrongProductTypeException {

        if (type == ProductType.BREAD) {
            return bread.size() >= amount;
        } else if (type == ProductType.BUN_WITH_ORANGE_JAM) {
            return bunWithJam.size() >= amount;
        } else if (type == ProductType.MILK) {
            return milk.size() >= amount;
        } else if (type == ProductType.ORANGE) {
            return oranges.size() >= amount;
        } else {
            throw exception;

        }
    }
}
