package cz.cvut.fel.omo.production.product;

import cz.cvut.fel.omo.Block;
import cz.cvut.fel.omo.api.Party;

public abstract class Operation implements Block {
    public final Party party;
    public final Product product;
    public final int day;
    private final String prevBlockHash;

    public Operation(Party party, Product product, int day, final String prevBlockHash) {
        this.party = party;
        this.product = product;
        this.day = day;
        this.prevBlockHash = prevBlockHash;
    }

    @Override
    public abstract String toString();

    @Override
    public String getMyHash() {
        int sum = 0;
        for (char ch : toString().toCharArray()) {
            sum += ch;
        }
        return "" + sum + product.getId();
    }

    @Override
    public String getPrevBlockHash() {
        return prevBlockHash;
    }


}
