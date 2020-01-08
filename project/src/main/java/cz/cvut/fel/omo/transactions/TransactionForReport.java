package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;

public class TransactionForReport {
    private Party sender;
    private Party reseiver;
    private ProductType type;
    private int amount;
    private int day;

    public TransactionForReport(Party sender, Party reseiver, ProductType type, int amount, int day) {
        this.sender = sender;
        this.reseiver = reseiver;
        this.type = type;
        this.amount = amount;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction from party ");
        sb.append(sender.getName());
        sb.append(" to party ");
        sb.append(reseiver.getName());
        sb.append(". Constains ");
        sb.append(amount).append(" units of ");
        sb.append(type);
        return sb.toString();
    }
}
