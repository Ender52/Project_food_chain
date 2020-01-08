package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.transactions.Request;

import java.util.ArrayList;
import java.util.List;

public interface Channel {

    List<Request> getAllRequests();


    BlockChain getBlockChain();


    ArrayList<Party> getParticipants();


    ProductType[] getMyProducts();


    void createRequest(ProductType type, int amount, Party sender);


    void attend(Party party);


    void leave(Party party);
}
