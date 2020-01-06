package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.production.product.ProductType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChannel {
    public List<Request> allRequests = new ArrayList<>();
    BlockChain blockChain;
    private ArrayList<PartyImpl> participants = new ArrayList<>();

    public AbstractChannel(BlockChain bc) {
        blockChain = bc;
    }

    public void createRequest(ProductType type, int amount, PartyImpl sender) {
        sendRequest(new Request(type, sender, amount, this));
    }

    public void doTransaction(Transaction transaction) {
        int transactionPrice = transaction.product.myPrice.amount;
        transaction.getReceiver().changeBalance(-1 * transactionPrice);
        transaction.getParty().changeBalance(transactionPrice);
        transaction.getReceiver().receiveProduct(transaction.product);

    }

    void sendRequest(Request request) {
        allRequests.add(request);
    }

    public void attend(PartyImpl party) {
        participants.add(party);
    }

    public void leave(PartyImpl party) {
        participants.remove(party);
    }
}
