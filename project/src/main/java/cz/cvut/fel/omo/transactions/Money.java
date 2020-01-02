package cz.cvut.fel.omo.transactions;

public class Money {
    public int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public void add(int pAmount) {
        amount += pAmount;
    }

}
