package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.transactions.Request;

/**
 * Channel where Parties send requests to each other
 */
public interface Channel {

    /**
     * @return blockchain
     */
    BlockChain getBlockChain();

    /**
     * @return productTypes that are possible to exchange in the given channel
     */
    ProductType[] getMyProducts();


    /**
     * Sends request to all participants of channel
     *
     * @param request
     */
    void sendRequest(Request request);

    /**
     * Removal of the request from the request queue
     *
     * @param request processed request that has to be removed
     */
    void deleteRequest(Request request);

}
