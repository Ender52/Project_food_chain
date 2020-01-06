package cz.cvut.fel.omo;

import cz.cvut.fel.omo.production.product.ProductType;

import java.util.List;

public interface Shop {
    void sellProduct(ProductType p, int amount);

    List<ProductType> showAsortiment();
}
