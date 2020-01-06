package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

public interface Storage {
    void put(Product[] products) throws WrongProductTypeException;

    Product get(ProductType type);

    Product[] takeProducts(ProductType type, int amount);

    boolean has(ProductType type, int amount) throws WrongProductTypeException;

}
