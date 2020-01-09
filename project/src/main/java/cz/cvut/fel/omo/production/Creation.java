package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.api.OperationVisitor;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Creation extends Operation {


    public Creation(Party party, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
    }


    @Override
    public void accept(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
