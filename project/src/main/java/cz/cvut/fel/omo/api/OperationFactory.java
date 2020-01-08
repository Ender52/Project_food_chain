package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public interface OperationFactory {
    Operation createOperation(String type, Product product);
}
