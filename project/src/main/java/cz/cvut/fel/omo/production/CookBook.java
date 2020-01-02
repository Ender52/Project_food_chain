package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.production.product.ProductType;

public class CookBook {
    public static ProductType[] getRecipe(ProductType type) {
        if (type == ProductType.BREAD) {
            return new ProductType[]{ProductType.WHEAT};
        } else if (type == ProductType.BUN_WITH_ORANGE_JAM) {
            return new ProductType[]{ProductType.WHEAT, ProductType.ORANGE};
        } else if (type == ProductType.SAUSAGE) {
            return new ProductType[]{ProductType.MEAT};
        } else {
            return new ProductType[]{};
        }
    }
}
