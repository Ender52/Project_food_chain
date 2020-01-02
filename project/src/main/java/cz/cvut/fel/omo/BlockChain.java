package cz.cvut.fel.omo;

import cz.cvut.fel.omo.transactions.*;

import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    public final MilkProductionChannel milkProductionChannel = new MilkProductionChannel(this);
    public final BakeryChannel bakeryChannel = new BakeryChannel(this);
    public final OrangesChannel orangesChannel = new OrangesChannel(this);
    public final MeatProductionChannel meatProductionChannel = new MeatProductionChannel(this);
    List<Block> chain = new ArrayList<>();
    private int size = 0;

    public void addBlock(Transaction transaction) {
        if (size == 0) chain.add(new Block(transaction, size));
        else chain.add(new Block(transaction, size, chain.get(chain.size() - 1).myHash));
        size++;
        System.out.println("NEW BLOCK , SIZE = " + size);
    }

}
