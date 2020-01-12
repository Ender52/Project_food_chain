package cz.cvut.fel.omo.api.operations;

import cz.cvut.fel.omo.api.OperationVisitor;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.product.Product;

public class Transaction extends Operation {
    private Party receiver;


    public Transaction(Party party, Party receiver, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
        this.receiver = receiver;
        int transactionPrice = product.myPrice.amount;
        getReceiver().changeBalance(-1 * transactionPrice);
        getParty().changeBalance(transactionPrice);
    }

    public Party getParty() {
        return party;
    }

    public Party getReceiver() {
        return receiver;
    }


    @Override
    public void accept(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
