package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.BlockChain;
import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;
import cz.cvut.fel.omo.transactions.Transaction;

import java.util.List;

public interface Party {

    String getName();

    Production getMyProduction();

    BlockChain getBlockChain();

    Money getWallet();

    EcoSystem getEcoSystem();

    List<Channel> getMyChannels();

    ProductType[] getMyProducts();

    int getId();

    /**
     * Creation of request
     *
     * @param type   type of requested products
     * @param amount amount of requested products
     */
    void createRequest(ProductType type, int amount);

    /**
     * Check if any requests are available in channels that the given party attends
     */
    void checkRequestsToMe();

    /**
     * Produce products from supplies that are located in the belonging storage
     */
    void work();

    /**
     * Produce products for the accepted request
     *
     * @param type   type of requested products
     * @param amount amount of requested products
     */
    void startProduceProducts(ProductType type, int amount);

    /**
     * Buy supplies to start the production of products
     *
     * @param type   type of requested products
     * @param amount amount of requested products
     */
    void buyProducts(ProductType type, int amount);

    /**
     * Respond to the accepted request that invokes the production of requested products
     *
     * @param request the given request to respond to
     */
    void responseToRequest(Request request);

    /**
     * Receive product from another party
     *
     * @param product the given product that is received
     */
    void receiveProduct(Product product);

    /**
     * Update balance in case of earning or spending money
     *
     * @param amount the given amount of money that changes balance
     */
    void changeBalance(int amount);

    /**
     * Create transaction between two parties when the given party exchanges product with another party
     * and add this transaction to the block chain
     *
     * @param receiver the second party that was involved in the transaction
     * @param product  the given product that is exchanged
     * @return the executed transaction
     */
    Transaction createTransaction(Party receiver, Product product);

    void createOp(String type, Product product);

    void doTransaction(Request request);

}
