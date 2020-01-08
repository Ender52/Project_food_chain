package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;

public class Producing extends State {
    private int amount;
    private int produced = 0;
    private ProductType[] components;

    public Producing(ProductionProcess context) {
        super(context);
        amount = context.getAmount();
        components = context.getComponents();
        period = 1 + amount / 10;
    }

    @Override
    protected void process() {
        if (amount >= 10) {
            Product[] newProducts = new Product[10];
            Product[][] allComps = new Product[components.length][10];
            int k = 0;
            for (ProductType type : components) {
                try {
                    allComps[k] = context.getProduction().getMyStorage().takeProducts(type, 10);
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
                for (Product p : allComps[k]) context.getProduction().getOwner().createOp("Take", p);
                k++;
            }
            for (int i = 0; i < 10; ++i) {
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

            amount -= 10;

        } else {
            Product[] newProducts = new Product[amount];
            Product[][] allComps = new Product[components.length][amount];
            int k = 0;
            for (ProductType type : components) {
                try {
                    allComps[k] = context.getProduction().getMyStorage().takeProducts(type, amount);
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
                for (Product p : allComps[k]) context.getProduction().getOwner().createOp("Take", p);

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
