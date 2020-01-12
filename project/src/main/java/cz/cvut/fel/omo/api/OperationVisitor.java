package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.Creation;
import cz.cvut.fel.omo.production.PutIntoStorage;
import cz.cvut.fel.omo.production.TakenFromStorage;
import cz.cvut.fel.omo.transactions.Transaction;

/**
 * Visitor for classes extended Operation class
 */
public interface OperationVisitor {
    /**
     * Visit
     *
     * @param creation
     */
    void visit(Creation creation);

    /**
     * Visit
     * @param putIntoStorage
     */
    void visit(PutIntoStorage putIntoStorage);

    /**
     * Visit
     * @param takenFromStorage
     */

    void visit(TakenFromStorage takenFromStorage);

    /**
     * Visit
     * @param transaction
     */
    void visit(Transaction transaction);
}
