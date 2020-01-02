public class Product implements Reportable {
    public final ProductType type;
    public final Production myProduction;
    public final Money myPrice;
    private final Reportable[] components;

    public Product(ProductType type, Product[] components, Production production) {
        this.type = type;
        this.components = components;
        myProduction = production;
        int price = 0;
        for (Product p : components) {
            price += p.myPrice.amount;
        }
        myPrice = new Money(price + 1);
    }

    @Override
    public void report() {
        System.out.println();
    }


}
