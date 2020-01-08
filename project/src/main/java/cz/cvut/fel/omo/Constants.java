package cz.cvut.fel.omo;

import cz.cvut.fel.omo.api.ProductType;

public class Constants {
    static final int MILK = 1000000;
    static final int WHEAT = 2000000;
    static final int MEAT = 3000000;
    static final int BREAD = 4000000;
    static final int ORANGE = 5000000;
    static final int BUN_WITH_ORANGE_JAM = 6000000;
    static final int SAUSAGE = 7000000;
    public static final String TYPES = "MOWB";
    public static int getProductTypeNumber(ProductType type) {
        if (type == ProductType.WHEAT) {
            return WHEAT;
        } else if (type == ProductType.MILK) {
            return MILK;
        } else if (type == ProductType.MEAT) {
            return MEAT;
        } else if (type == ProductType.BREAD) {
            return BREAD;
        } else if (type == ProductType.ORANGE) {
            return ORANGE;
        } else if (type == ProductType.BUN_WITH_ORANGE_JAM) {
            return BUN_WITH_ORANGE_JAM;
        } else if (type == ProductType.SAUSAGE) {
            return SAUSAGE;
        }
        return 0;
    }
}
