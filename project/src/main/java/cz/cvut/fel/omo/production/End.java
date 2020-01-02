package cz.cvut.fel.omo.production;

public class End extends State {
    public End(ProductionProcess context) {
        super(context);
        period = 1;
    }

    @Override
    protected void process() {

    }

    @Override
    protected void changeToNextState() {

    }
}
