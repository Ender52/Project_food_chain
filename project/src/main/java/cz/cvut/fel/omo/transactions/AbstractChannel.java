package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChannel {
    public List<Request> allRequests = new ArrayList<>();
    BlockChain blockChain;
    private ArrayList<Party> participants = new ArrayList<>();

    public AbstractChannel(BlockChain bc) {
        blockChain = bc;
    }

    public void createRequest(ProductType type, int amount, Party sender) {
        sendRequest(new Request(type, sender, amount, this));
    }

    public void doTransaction(Transaction transaction) {
        transaction.getReceiver().receiveProducts(transaction.getProducts());
        int transactionPrice = transaction.getProducts()[0].myPrice.amount * transaction.getProducts().length;
        transaction.getReceiver().changeBalance(-1 * transactionPrice);
        transaction.getSender().changeBalance(transactionPrice);
        blockChain.addBlock(transaction);
    }

    void sendRequest(Request request) {
        allRequests.add(request);
    }

    public void attend(Party party) {
        participants.add(party);
    }

    public void leave(Party party) {
        participants.remove(party);
    }
}
