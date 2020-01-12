package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.product.Product;

/**
 * Production like fabric, class that is responsible for creation of products
 */
public interface Production {
    int getId();

    ProductType[] getMyProducts();

    Party getOwner();

    /**
     * Method that starts producing product of type
     *
     * @param type   in
     * @param amount Production process is created inside
     */
    void startProducingProducts(ProductType type, int amount);

    /**
     * Continue production process producing products,
     * or if there is no process do nothing
     * @return array of Products - result of production
     */
    Product[] produce();

    /**
     * generates new unique Id for Product
     * @param productType of product to generate Id
     * @return id
     */
    int getIdForNewProduct(ProductType productType);
}
