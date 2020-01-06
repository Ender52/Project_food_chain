package cz.cvut.fel.omo.production.product;

import cz.cvut.fel.omo.parties.Party;

public abstract class Operation {
    public Party party;
    public Product[] products;
    public int day;

    public Operation(Party party, Product[] products, int day) {
        this.party = party;
        this.products = products;
        this.day = day;
        for (Product p : products) p.addOperation(this);
    }

    @Override
    public abstract String toString();

}
