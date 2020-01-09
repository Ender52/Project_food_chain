package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.Creation;
import cz.cvut.fel.omo.production.PutIntoStorage;
import cz.cvut.fel.omo.production.TakenFromStorage;
import cz.cvut.fel.omo.transactions.Transaction;

public interface OperationVisitor {
    void visit(Creation creation);

    void visit(PutIntoStorage putIntoStorage);

    void visit(TakenFromStorage takenFromStorage);

    void visit(Transaction transaction);
}
