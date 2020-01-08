package cz.cvut.fel.omo.api.impl;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.api.Channel;
import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.transactions.Request;
import cz.cvut.fel.omo.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class ChannelImpl implements Channel {
    protected List<Request> allRequests = new ArrayList<>();
    protected BlockChain blockChain;
    protected ArrayList<Party> participants = new ArrayList<>();
    protected ProductType[] myProducts;


    public ChannelImpl(BlockChain bc) {
        blockChain = bc;
    }


    public List<Request> getAllRequests() {
        return allRequests;
    }

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public ArrayList<Party> getParticipants() {
        return participants;
    }

    public ProductType[] getMyProducts() {
        return myProducts;
    }

    public void createRequest(ProductType type, int amount, Party sender) {
        sendRequest(new Request(type, sender, amount, this));
    }

    public void doTransaction(Transaction transaction) {
        int transactionPrice = transaction.product.myPrice.amount;
        transaction.getReceiver().changeBalance(-1 * transactionPrice);
        transaction.getParty().changeBalance(transactionPrice);
        transaction.getReceiver().receiveProduct(transaction.product);
    }

    private void sendRequest(Request request) {
        allRequests.add(request);
    }

    public void attend(Party party) {
        participants.add(party);
    }

    public void leave(Party party) {
        participants.remove(party);
    }
}
