package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.operations.Operation;
import cz.cvut.fel.omo.api.product.Product;

/**
 * Factory for Operations
 */
public interface OperationFactory {
    /**
     * Creates Operation
     *
     * @param type    of Operation to be created
     * @param product of Operation to be created
     * @return created operation
     */
    Operation createOperation(String type, Product product);
}
