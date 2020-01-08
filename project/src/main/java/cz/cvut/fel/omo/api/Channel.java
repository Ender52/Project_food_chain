package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.transactions.Request;
import cz.cvut.fel.omo.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface Channel {

    List<Request> getAllRequests();


    BlockChain getBlockChain();


    ArrayList<Party> getParticipants();


    ProductType[] getMyProducts();


    void createRequest(ProductType type, int amount, Party sender);


    void doTransaction(Transaction transaction);


    void attend(Party party);


    void leave(Party party);
}
