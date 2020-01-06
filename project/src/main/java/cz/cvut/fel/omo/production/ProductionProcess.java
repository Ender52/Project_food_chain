package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;

public class ProductionProcess {

    private ProductType myType;
    private Production production;
    private ProductType[] components;
    private int amount;
    private State state;
    private Product[] result;

    public ProductionProcess(ProductType type, int amount, Production production) {
        myType = type;
        this.amount = amount;
        result = new Product[amount];
        this.production = production;
        components = CookBook.getRecipe(myType);
        if (components.length == 0) state = new Producing(this);
        else state = new Preparation(this);
    }

    public Product[] getResult() {
        return result;
    }

    public void setResult(Product[] result) {
        this.result = result;
    }

    public ProductType[] getComponents() {
        return components;
    }

    public ProductType getMyType() {
        return myType;
    }

    public Production getProduction() {
        return production;
    }

    public int getAmount() {
        return amount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void timeLapseTick() {
        state.timeLapseTick();
    }
}
