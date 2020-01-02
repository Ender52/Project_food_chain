public class ShopProduction extends Production {
    ShopProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new ShopStorage();
    }

    @Override
    public void produce() {

    }
}
