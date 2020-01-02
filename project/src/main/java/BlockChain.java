import java.util.ArrayList;
import java.util.List;


public class BlockChain {
    public final MilkProductionChannel milkProductionChannel = new MilkProductionChannel(this);
    List<Block> chain = new ArrayList<>();
    private int size = 0;

    void addBlock(Transaction transaction) {
        if (size == 0) chain.add(new Block(transaction, size));
        else chain.add(new Block(transaction, size, chain.get(chain.size() - 1).myHash));
        size++;
        System.out.println("NEW BLOCK , SIZE = " + size);
    }

}
