package cz.cvut.fel.omo;

import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.transactions.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    public final MilkProductionChannel milkProductionChannel = new MilkProductionChannel(this);
    public final BakeryChannel bakeryChannel = new BakeryChannel(this);
    public final OrangesChannel orangesChannel = new OrangesChannel(this);
    public final MeatProductionChannel meatProductionChannel = new MeatProductionChannel(this);
    private List<Operation> chain = new ArrayList<>();
    private List<Operation> reservChain = new ArrayList<>();
    private TransactionReporter transactionReporter = new TransactionReporter();
    private List<Pair<Integer, String>> faked = new ArrayList<>();
    private int size = 0;
    private boolean sequred = true;
    private DoubleSpendingRegulator regulator = new DoubleSpendingRegulator();

    public DoubleSpendingRegulator getRegulator() {
        return regulator;
    }

    public List<Pair<Integer, String>> getFaked() {
        return faked;
    }

    public TransactionReporter getTransactionReporter() {
        return transactionReporter;
    }

    public boolean isSequred() {
        return sequred;
    }

    public List<Operation> getChain() {
        return chain;
    }

    public void addBlock(Operation block) {
        chain.add(block);
        reservChain.add(block);
        if (block instanceof Transaction) {
            regulator.validateTransaction((Transaction) block);
        }
        size++;
    }

    public void sequre(int day) {
        for (int i = 0; i < size - 1; i++) {
            if (!chain.get(i).getMyHash().equals(chain.get(i + 1).getPrevBlockHash())) {
                System.err.println("Somebody tried to fake block. Block #" + i);
                faked.add(new Pair<>(day, "Somebody tried to fake block. Block #" + i));
                chain.set(i, reservChain.get(i));

            }
        }
    }

    public void addTransactionForReport(TransactionForReport transaction) {
        transactionReporter.addTransaction(transaction);
    }


}
