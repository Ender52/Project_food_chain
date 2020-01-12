package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.parties.Bakery;
import cz.cvut.fel.omo.api.parties.Distributor;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.transactions.Request;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartyTest {

    @Test
    public void buyProductsCreateRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Party test_baker2 = new Bakery("test3", 3);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        assertTrue(test_baker2.getRequestsToMe().size() == 0);
        test_distr.buyProducts(ProductType.BREAD, 5);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
        assertTrue(test_baker2.getRequestsToMe().size() == 1);
    }

    @Test
    public void responseToRequestDeletesRequest() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Party test_baker2 = new Bakery("test3", 3);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 1, channel);
        test_baker.getRequestsToMe().add(test_request);
        test_baker2.getRequestsToMe().add(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
        assertTrue(test_baker2.getRequestsToMe().size() == 1);
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 1));
        test_baker.responseToRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        assertTrue(test_baker2.getRequestsToMe().size() == 0);

    }

    @Test
    public void doTransactionCreatestransaction() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 1, channel);
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 1));
        assertTrue(test_baker.getBlockChain().getTransactionReporter().getTransactions().size() == 0);
        test_baker.doTransaction(test_request);
        assertTrue(test_baker.getBlockChain().getTransactionReporter().getTransactions().size() == 1);
    }

    @Test
    public void doTransactionSellProducts() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 1, channel);
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 1));
        assertFalse(test_distr.getMyStorage().has(ProductType.BREAD, 1));
        test_baker.doTransaction(test_request);
        assertTrue(test_distr.getMyStorage().has(ProductType.BREAD, 1));
    }

    @Test
    public void doTransactionNotEnoughProducts() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 1));
        assertFalse(test_distr.getMyStorage().has(ProductType.BREAD, 1));
        test_baker.doTransaction(test_request);
        assertFalse(test_distr.getMyStorage().has(ProductType.BREAD, 1));
    }

    @Test
    public void receiveProductsReceiveProducts() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Product test_product = new Product(ProductType.BREAD, new Product[0], 1);
        assertFalse(test_distr.getMyStorage().has(ProductType.BREAD, 1));
        test_distr.receiveProduct(test_product);
        assertTrue(test_distr.getMyStorage().has(ProductType.BREAD, 1));
    }

    @Test
    public void receiveProductsWrongType() {
        Party test_baker = new Bakery("test", 1);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Product test_product = new Product(ProductType.MILK, new Product[0], 1);
        test_baker.receiveProduct(test_product);
        boolean hasAny = false;
        assertFalse(hasAny);
    }

    @Test
    public void balanceChangeBalanceChange() {
        Party test_baker = new Bakery("test", 1);
        assertEquals(100, test_baker.getWallet().amount);
        test_baker.changeBalance(100);
        assertEquals(200, test_baker.getWallet().amount);
    }


    @Test
    public void violateChangeDateOfProductionViolate() {
        Party test_baker = new Bakery("test", 1);
        Product test_product = new Product(ProductType.BREAD, new Product[0], 1);
        test_baker.createOp("Creation", test_product);
        test_baker.createOp("Put", test_product);
        test_baker.getBlockChain().secure(0);
        assertEquals(0, test_baker.getBlockChain().getFaked().size());
        test_baker.violateChangeDateOfProduction(test_product);
        test_baker.getBlockChain().secure(0);
        assertEquals(1, test_baker.getBlockChain().getFaked().size());

    }

    @Test
    public void violateCDoubleSpend() throws WrongProductTypeException {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Distributor("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 1));
        test_baker.getMyStorage().put(new Product(ProductType.BREAD, new Product[0], 2));
        assertEquals(0, test_baker.getBlockChain().getRegulator().getReportStrings().size());
        test_baker.violateDoubleSpend(test_request);
        assertEquals(1, test_baker.getBlockChain().getRegulator().getReportStrings().size());

    }
}
