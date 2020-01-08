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

    void createRequest(ProductType type, int amount);

    void checkRequestsToMe();

    void work();

    void startProduceProducts(ProductType type, int amount);

    void buyProducts(ProductType type, int amount);

    void responseToRequest(Request request);

    void receiveProduct(Product product);

    void changeBalance(int amount);

    Transaction createTransaction(Party receiver, Product product);

    void createOp(String type, Product product);

    void doTransaction(Request request);

}
