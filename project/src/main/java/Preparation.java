public class Preparation extends State {
    private int i = 0;
    private State nextState;

    public Preparation(ProductionProcess context) {
        super(context);
        period = context.getComponents().length;

    }

    @Override
    protected void process() {
        int am = context.getAmount();
        ProductType productType = context.getComponents()[i];
        context.getProduction().getOwner().buyProducts(productType, am);
        i++;
    }


    @Override
    protected void changeToNextState() {
        context.setState(new Waiting(context));
    }
}
