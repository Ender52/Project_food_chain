package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.exceptions.OmoException;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;

import java.util.List;

public interface Storage {


    /**
     * @return
     */
    List<ProductType> getMyProducts();

    /**
     * @param products
     * @throws WrongProductTypeException
     */
    void put(Product[] products) throws WrongProductTypeException;

    /**
     * @param product
     * @throws WrongProductTypeException
     */
    void put(Product product) throws WrongProductTypeException;

    /**
     * sdfsdfsdfsfefwefwewef
     * @param type
     * @return
     * @throws WrongProductTypeException
     */
    Product get(ProductType type) throws WrongProductTypeException;

    /**
     * @param type
     * @param amount
     * @return
     * @throws WrongProductTypeException
     */
    Product[] takeProducts(ProductType type, int amount) throws OmoException;

    int size(ProductType type) throws WrongProductTypeException;

    boolean has(ProductType type, int amount) throws WrongProductTypeException;

}
