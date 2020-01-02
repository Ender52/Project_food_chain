package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Product;

public class Transaction {
    private Party sender;
    private Party receiver;
    private Product[] products;


    public Transaction(Party sender, Party receiver, Product[] products) {
        this.sender = sender;
        this.receiver = receiver;
        this.products = products;
    }

    public Party getSender() {
        return sender;
    }

    public Party getReceiver() {
        return receiver;
    }

    public Product[] getProducts() {
        return products;
    }

}
