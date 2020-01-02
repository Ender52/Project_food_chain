public class ProductionProcess {

    private ProductType myType;
    private Production production;
    private ProductType[] components;
    private int amount;
    private State state;

    public ProductionProcess(ProductType type, int amount, Production production) {
        myType = type;
        this.amount = amount;
        this.production = production;
        components = CookBook.getRecipe(myType);
        if (components.length == 0) state = new Producing(this);
        else state = new Preparation(this);
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

    void setState(State state) {
        this.state = state;
    }

    public void timeLapseTick() {
        state.timeLapseTick();
    }
}
