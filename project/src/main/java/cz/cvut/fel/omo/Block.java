package cz.cvut.fel.omo;

import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.production.product.Product;

public class Block {
    Operation operation;
    String previousBlockHash;

    Block(Operation operation, String prevHash) {
        this.operation = operation;
        previousBlockHash = prevHash;
    }

    Block(Operation operation) {
        this.operation = operation;
        int sum = 0;
    }

    public String getMyHash() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (char ch : operation.toString().toCharArray()) {
            i += ch;
        }
        sb.append(i);
        for (Product product : operation.products) {
            sb.append(product.getId());
        }
        return sb.toString();
    }

}
