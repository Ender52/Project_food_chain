package cz.cvut.fel.omo;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Shop;
import javafx.util.Pair;

import java.util.List;

public class Customer {
    Shop shop;

    public Customer(Shop shop) {
        this.shop = shop;
    }

    public void act() {
        List<ProductType> productTypes = shop.showAsortiment();
        Pair<ProductType, Integer> pair = pickRandom(productTypes);
        if (pair == null) return;
        shop.sellProduct(pair.getKey(), pair.getValue());
    }

    private Pair<ProductType, Integer> pickRandom(List<ProductType> types) {
        int length = types.size();
        if (length == 0) return null;
        int randType = (int) (Math.random() * length);
        int randAmount = (int) (Math.random() * 5 + 1);
        return new Pair<>(types.get(randType), randAmount);
    }

}
