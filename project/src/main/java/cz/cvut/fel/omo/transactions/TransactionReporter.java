package cz.cvut.fel.omo.transactions;

import java.util.ArrayList;

public class TransactionReporter {
    private ArrayList<TransactionForReport> transactions = new ArrayList<>();

    public ArrayList<TransactionForReport> getTransactions() {
        return transactions;
    }

    public void addTransaction(TransactionForReport transaction) {
        transactions.add(transaction);
    }

    public String getReportString() {
        StringBuilder sb = new StringBuilder();
        int day = 0;
        for (TransactionForReport transaction : transactions) {
            if (transaction.getDay() != day) {
                day = transaction.getDay();
                sb.append("DAY ");
                sb.append(transaction.getDay());
                sb.append(" :\n");
            }
            sb.append(transaction.report());
            sb.append("\n");
        }
        return sb.toString();
    }
}
