package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.operations.Creation;
import cz.cvut.fel.omo.api.operations.PutIntoStorage;
import cz.cvut.fel.omo.api.operations.TakenFromStorage;
import cz.cvut.fel.omo.api.operations.Transaction;

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
