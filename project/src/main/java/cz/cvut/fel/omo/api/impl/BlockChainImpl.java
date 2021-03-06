package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.api.BlockChain;
import cz.cvut.fel.omo.api.channels.*;
import cz.cvut.fel.omo.api.operations.Operation;
import cz.cvut.fel.omo.api.operations.Transaction;
import cz.cvut.fel.omo.transactions.DoubleSpendingRegulator;
import cz.cvut.fel.omo.transactions.TransactionForReport;
import cz.cvut.fel.omo.transactions.TransactionReporter;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class BlockChainImpl implements BlockChain {
    public final MilkProductionChannel milkProductionChannel = new MilkProductionChannel(this);
    public final BakeryChannel bakeryChannel = new BakeryChannel(this);
    public final OrangesChannel orangesChannel = new OrangesChannel(this);
    public final MeatProductionChannel meatProductionChannel = new MeatProductionChannel(this);
    public final LogisticChannel logisticChannel = new LogisticChannel(this);
    public final LogisticChannel shopChannel = new LogisticChannel(this);

    private List<Operation> chain = new ArrayList<>();
    private List<Operation> reservChain = new ArrayList<>();
    private TransactionReporter transactionReporter = new TransactionReporter();
    private List<Pair<Integer, String>> faked = new ArrayList<>();
    private int size = 0;
    private DoubleSpendingRegulator regulator = new DoubleSpendingRegulator();


    @Override
    public DoubleSpendingRegulator getRegulator() {
        return regulator;
    }

    @Override
    public List<Pair<Integer, String>> getFaked() {
        return faked;
    }

    @Override

    public TransactionReporter getTransactionReporter() {
        return transactionReporter;
    }

    @Override
    public List<Operation> getChain() {
        return chain;
    }

    @Override
    public void addBlock(Operation block) {
        chain.add(block);
        reservChain.add(block);
        if (block instanceof Transaction) {
            regulator.validateTransaction((Transaction) block);
        }
        size++;
    }

    @Override
    public void secure(int day) {
        for (int i = 0; i < size - 1; i++) {
            if (!chain.get(i).getMyHash().equals(chain.get(i + 1).getPrevBlockHash())) {
                System.err.println("Somebody tried to fake block. Block #" + i);
                faked.add(new Pair<>(day, "Somebody tried to fake block. Block #" + i));
                chain.set(i, reservChain.get(i));

            }
        }
    }

    @Override
    public void addTransactionForReport(TransactionForReport transaction) {
        transactionReporter.addTransaction(transaction);
    }


}
