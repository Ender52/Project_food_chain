package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

public class Producing extends State {
    private int amount;
    private int produced = 0;
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
            Product[][] allComps = new Product[components.length][100];
            int k = 0;
            for (ProductType type : components) {
                allComps[k] = context.getProduction().getMyStorage().takeProducts(type, 100);
                context.getProduction().owner.createOperation("Take", allComps[k]);
                k++;
            }
            for (int i = 0; i < 100; ++i) {
                if (components.length == 0) {
                    newProducts[i] = new Product(context.getMyType(), new Product[0], context.getProduction().getIdForNewProduct(context.getMyType()));
                } else {
                    Product[] comps = new Product[components.length];
                    int j = 0;
                    for (ProductType type : components) {
                        comps[j] = allComps[j][i];
                        j++;
                    }
                    newProducts[i] = new Product(context.getMyType(), comps, context.getProduction().getIdForNewProduct(context.getMyType()));
                }
            }

            for (Product p : newProducts) {
                context.getResult()[produced++] = p;
            }

            amount -= 100;

        } else {
            Product[] newProducts = new Product[amount];
            Product[][] allComps = new Product[components.length][amount];
            int k = 0;
            for (ProductType type : components) {
                allComps[k] = context.getProduction().getMyStorage().takeProducts(type, amount);
                context.getProduction().owner.createOperation("Take", allComps[k]);
                k++;
            }
            for (int i = 0; i < amount; ++i) {
                if (components.length == 0) {
                    newProducts[i] = new Product(context.getMyType(), new Product[0], context.getProduction().getIdForNewProduct(context.getMyType()));
                } else {
                    Product[] comps = new Product[components.length];
                    int j = 0;
                    for (ProductType type : components) {
                        comps[j] = allComps[j][i];
                        j++;
                    }
                    newProducts[i] = new Product(context.getMyType(), comps, context.getProduction().getIdForNewProduct(context.getMyType()));
                }
            }
            for (Product p : newProducts) {
                context.getResult()[produced++] = p;
            }
        }
    }

    @Override
    protected void changeToNextState() {
        context.setState(new End(context));
    }
}
