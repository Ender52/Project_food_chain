package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.transactions.Request;

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
     * Creation of request in the given channel
     *
     * @param type   type of requested products
     * @param amount amount of requested products
     * @param sender sender of the request
     */
    void createRequest(ProductType type, int amount, Party sender);

    /**
     * Removal of the request from the request queue
     *
     * @param request processed request that has to be removed
     */
    void deleteRequest(Request request);

}
