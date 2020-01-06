package cz.cvut.fel.omo;

import cz.cvut.fel.omo.production.product.Operation;
import cz.cvut.fel.omo.transactions.*;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    public final MilkProductionChannel milkProductionChannel = new MilkProductionChannel(this);
    public final BakeryChannel bakeryChannel = new BakeryChannel(this);
    public final OrangesChannel orangesChannel = new OrangesChannel(this);
    public final MeatProductionChannel meatProductionChannel = new MeatProductionChannel(this);
    private List<Block> chain = new ArrayList<>();
    private int size = 0;
    private boolean sequred = true;
    private DoubleSpendingRegulator regulator = new DoubleSpendingRegulator();


    public boolean isSequred() {
        return sequred;
    }

    public List<Block> getChain() {
        return chain;
    }

    public void addBlock(Operation operation) {
        if (size == 0) chain.add(new Block(operation));
        else chain.add(new Block(operation, chain.get(chain.size() - 1).getMyHash()));
        if (operation instanceof Transaction) {
            regulator.validateTransaction((Transaction) operation);
        }
        size++;
        System.out.println("NEW BLOCK , SIZE = " + size);

    }

    public void sequre() {
        for (int i = 0; i < size - 1; i++) {
            if (!chain.get(i).getMyHash().equals(chain.get(i + 1).previousBlockHash)) {
                sequred = false;
                break;
            }
        }
    }


}
