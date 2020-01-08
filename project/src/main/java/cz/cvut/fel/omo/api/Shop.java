package cz.cvut.fel.omo.api;

import java.util.List;

public interface Shop {
    void sellProduct(ProductType p, int amount);

    List<ProductType> showAsortiment();
}
