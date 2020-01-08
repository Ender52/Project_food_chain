package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Transaction extends Operation {
    private Party receiver;


    public Transaction(Party party, Party receiver, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
        this.receiver = receiver;
    }

    public Party getParty() {
        return party;
    }

    public Party getReceiver() {
        return receiver;
    }


    @Override
    public String toString() {
        return "Product " + product.type + " in transaction from " + party.getName() + " to Party " + receiver.getName() + " on " + day + " day";

    }
}
