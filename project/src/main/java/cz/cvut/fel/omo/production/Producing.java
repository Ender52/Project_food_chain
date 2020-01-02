package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

public class Producing extends State {
    private int amount;
    private ProductType[] components;

    public Producing(ProductionProcess context) {
        super(context);
        amount = context.getAmount();
        components = context.getComponents();
        period = 1 + amount / 100;
    }

    @Override
    protected void process() {
        if (amount >= 100) {
            Product[] newProducts = new Product[100];

            for (int i = 0; i < 100; ++i) {
                if (components.length == 0) {
                    newProducts[i] = new Product(context.getMyType(), new Product[0], context.getProduction());
                } else {
                    Product[] comps = new Product[components.length];
                    int k = 0;
                    for (ProductType type : components) {
                        comps[k] = context.getProduction().getMyStorage().get(type);
                        k++;
                    }
                    newProducts[i] = new Product(context.getMyType(), comps, context.getProduction());
                }
            }
            try {
                context.getProduction().getMyStorage().put(newProducts);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
            amount -= 100;

        } else {
            Product[] newProducts = new Product[amount];
            for (int i = 0; i < amount; ++i) {
                if (components.length == 0) {
                    newProducts[i] = new Product(context.getMyType(), new Product[0], context.getProduction());
                } else {
                    Product[] comps = new Product[components.length];
                    int k = 0;
                    for (ProductType type : components) {
                        comps[k] = context.getProduction().getMyStorage().get(type);
                        k++;
                    }
                    newProducts[i] = new Product(context.getMyType(), comps, context.getProduction());
                }
            }
            try {
                context.getProduction().getMyStorage().put(newProducts);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void changeToNextState() {
        context.setState(new End(context));
    }
}
