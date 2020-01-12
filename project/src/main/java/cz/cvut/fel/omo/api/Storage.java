package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.exceptions.OmoException;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;

import java.util.List;

/**
 * Storage which every Party has
 */
public interface Storage {


    List<ProductType> getMyProducts();

    /**
     * Put array of Products
     * @param products
     * in Storage
     * @throws WrongProductTypeException
     */
    void put(Product[] products) throws WrongProductTypeException;

    /**
     Put one Product
     * @param product
     * in Storage
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
     * Take Products of
     * @param type
     * in
     * @param amount
     * from storage
     * @return array of Products
     * @throws OmoException
     */
    Product[] takeProducts(ProductType type, int amount) throws OmoException;

    /**
     * Takes
     *
     * @param type of Product you want to know about
     * @return number of Products in storage
     * @throws WrongProductTypeException
     */
    int size(ProductType type) throws WrongProductTypeException;

    /**
     * Checks if Products of type
     * @param type
     * in amount
     * @param amount
     * are stored
     * @return true if stored, otherwise false
     * @throws WrongProductTypeException
     */
    boolean has(ProductType type, int amount) throws WrongProductTypeException;

}
