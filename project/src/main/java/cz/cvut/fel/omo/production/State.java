package cz.cvut.fel.omo.production;

public abstract class State {
    protected ProductionProcess context;

    protected int period;

    public State(ProductionProcess context) {
        this.context = context;
    }

    public void timeLapseTick() {
        process();
        period--;
        if (period == 0) changeToNextState();
    }

    ;

    abstract protected void process();

    abstract protected void changeToNextState();

    public ProductionProcess getContext() {
        return context;
    }

    public void setContext(ProductionProcess context) {
        this.context = context;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
