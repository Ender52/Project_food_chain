public class MilkProduction extends Production {

    MilkProduction(Party manufacturer) {
        super(manufacturer);
        myStorage = new MilkFarmerStorage();
        myProducts.add(ProductType.MILK);
    }
}
