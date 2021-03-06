package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;

public class Waiting extends State {
    private ProductType[] products;

    public Waiting(ProductionProcess context) {
        super(context);
        products = context.getComponents();
        period = 1;
    }

    @Override
    protected void process() {
        boolean allProductsPrepared = false;
        for (ProductType type : products) {
            try {
                allProductsPrepared = context.getProduction().getOwner().getMyStorage().has(type, context.getAmount());

            } catch (WrongProductTypeException e) {
                System.err.println("WrongProductTypeException");
            }
            if (!allProductsPrepared) break;
        }
        if (!allProductsPrepared) period += 1;
    }

    @Override
    protected void changeToNextState() {
        context.setState(new Producing(context));
    }
}
