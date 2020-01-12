package cz.cvut.fel.omo.api.operations;

import cz.cvut.fel.omo.api.Block;
import cz.cvut.fel.omo.api.OperationVisitor;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.product.Product;

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

    public abstract void accept(OperationVisitor visitor);

}
