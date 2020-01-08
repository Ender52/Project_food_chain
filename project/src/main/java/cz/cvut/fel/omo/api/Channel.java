package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.transactions.Request;

public interface Channel {



    BlockChain getBlockChain();


    ProductType[] getMyProducts();


    void createRequest(ProductType type, int amount, Party sender);

    void deleteRequest(Request request);

}
