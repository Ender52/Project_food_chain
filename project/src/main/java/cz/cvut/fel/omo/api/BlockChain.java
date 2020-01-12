package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.operations.Operation;
import cz.cvut.fel.omo.transactions.DoubleSpendingRegulator;
import cz.cvut.fel.omo.transactions.TransactionForReport;
import cz.cvut.fel.omo.transactions.TransactionReporter;
import javafx.util.Pair;

import java.util.List;

/**
 * BlockChain in our system
 */
public interface BlockChain {

    DoubleSpendingRegulator getRegulator();

    List<Pair<Integer, String>> getFaked();

    TransactionReporter getTransactionReporter();

    List<Operation> getChain();

    /**
     * Adds new block to chain
     *
     * @param block
     */
    void addBlock(Operation block);

    /**
     * Secure the chain
     *
     * @param day when secure
     */
    void secure(int day);

    /**
     * Adds transaction for report
     *
     * @param transaction to add
     */
    void addTransactionForReport(TransactionForReport transaction);
}
