package cz.cvut.fel.omo.production;

import cz.cvut.fel.omo.api.OperationVisitor;
import cz.cvut.fel.omo.api.impl.PartyImpl;
import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class PutIntoStorage extends Operation {


    public PutIntoStorage(PartyImpl party, Product product, int day, String prevBlockHash) {
        super(party, product, day, prevBlockHash);
    }


    @Override
    public void accept(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
