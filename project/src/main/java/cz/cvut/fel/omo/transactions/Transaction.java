package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Transaction extends Operation {
    private PartyImpl receiver;


    public Transaction(PartyImpl party, PartyImpl receiver, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
        this.receiver = receiver;
    }

    public PartyImpl getParty() {
        return party;
    }

    public PartyImpl getReceiver() {
        return receiver;
    }


    @Override
    public String toString() {
        return "Product " + product.type + " in transaction from " + party.name + " to Party " + receiver.name + " on " + day + " day";

    }
}
