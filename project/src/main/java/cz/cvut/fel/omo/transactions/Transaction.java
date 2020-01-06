package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Transaction extends Operation {
    private Party receiver;


    public Transaction(Party party, Party receiver, Product[] products, int day) {
        super(party, products, day);
        this.receiver = receiver;
    }

    public Party getParty() {
        return party;
    }

    public Party getReceiver() {
        return receiver;
    }

    public Product[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Product " + products[0].type + " in transaction from " + party.name + " to Party " + receiver.name + " on " + day + " day";

    }
}
