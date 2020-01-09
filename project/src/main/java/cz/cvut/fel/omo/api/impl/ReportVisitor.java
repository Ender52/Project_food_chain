package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.OperationVisitor;
import cz.cvut.fel.omo.production.Creation;
import cz.cvut.fel.omo.production.PutIntoStorage;
import cz.cvut.fel.omo.production.TakenFromStorage;
import cz.cvut.fel.omo.transactions.Transaction;

public class ReportVisitor implements OperationVisitor {
    private StringBuilder stringBuilder = new StringBuilder();

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    @Override
    public void visit(Creation creation) {
        stringBuilder.append("Product " + creation.product.type + " was created by " + creation.party.getName() + " on " + creation.day + " day\n");

    }

    @Override
    public void visit(PutIntoStorage putIntoStorage) {
        stringBuilder.append("Product " + putIntoStorage.product.type + " was putted into " + putIntoStorage.party.getName() + "`s storage on " + putIntoStorage.day + " day\n");

    }

    @Override
    public void visit(TakenFromStorage takenFromStorage) {
        stringBuilder.append("Product " + takenFromStorage.product.type + " was taken from" + takenFromStorage.party.getName() + "`s storage on " + takenFromStorage.day + " day\n");

    }

    @Override
    public void visit(Transaction transaction) {
        stringBuilder.append("Product " + transaction.product.type + " in transaction from " + transaction.party.getName() + " to Party " + transaction.getReceiver().getName() + " on " + transaction.day + " day\n");

    }
}
