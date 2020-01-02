public class CookBook {
    public static ProductType[] getRecipe(ProductType type) {
        if (type == ProductType.BREAD) {
            return new ProductType[]{ProductType.WHEAT};
        } else if (type == ProductType.MILK) {
            return new ProductType[]{};
        } else {
            return new ProductType[]{};
        }
    }
}
