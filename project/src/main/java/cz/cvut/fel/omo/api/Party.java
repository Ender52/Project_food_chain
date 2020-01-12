package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.impl.BlockChainImpl;
import cz.cvut.fel.omo.api.operations.Transaction;
import cz.cvut.fel.omo.api.product.Product;
import cz.cvut.fel.omo.transactions.Money;
import cz.cvut.fel.omo.transactions.Request;

import java.util.List;

/**
 * Party is main member of FoodChain ecoSystem
 */
public interface Party {

    List<Request> getRequestsToMe();

    String getName();

    BlockChainImpl getBlockChain();

    Money getWallet();

    Storage getMyStorage();

    EcoSystem getEcoSystem();

    List<Channel> getMyChannels();

    ProductType[] getMyProducts();

    int getId();


    /**
     * Check if any requests are available in channels that the given party attends
     */
    void checkRequestsToMe();

    /**
     * Produce products from supplies that are located in the belonging storage
     */
    void work();


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

    /**
     * Creates Operation,
     * method delegates to CreateOperation()  of OperationFactory
     *
     * @param type    of Operation
     * @param product of Operation
     */
    void createOp(String type, Product product);

    /**
     * Creates transaction, sell products
     * @param request from which transaction is build
     */
    void doTransaction(Request request);

    /**
     * Violate system rules by changing data in block of BlockChain
     * changes day of creation
     * @param product which creation date changes
     */
    void violateChangeDateOfProduction(Product product);

    /**
     *Violate system rules by selling one product twice
     * @param request
     */
    void violateDoubleSpend(Request request);
}
