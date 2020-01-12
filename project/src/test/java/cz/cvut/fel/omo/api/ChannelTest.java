package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.parties.Bakery;
import cz.cvut.fel.omo.transactions.Request;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
public class ChannelTest {


    @Test
    public void createRequestCreatesFirstRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        channel.createRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
    }

    @Test
    public void createRequestCreatesSecondRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        channel.createRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
        channel.createRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 2);
    }

    @Test
    public void createRequestSenderDoesntReceiveHisRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        assertTrue(test_distr.getRequestsToMe().size() == 0);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        channel.createRequest(test_request);
        assertTrue(test_distr.getRequestsToMe().size() == 0);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
    }

    @Test
    public void createRequestWrongProductType() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.MILK, test_distr, 2, channel);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        channel.createRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
    }
    @Test
    public void deleteRequestDeletesRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        channel.createRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 1);
        channel.deleteRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
    }

    @Test
    public void deleteRequestNotExistingRequest() {
        Party test_baker = new Bakery("test", 1);
        Party test_distr = new Bakery("test2", 2);
        Channel channel = test_baker.getBlockChain().bakeryChannel;
        Request test_request = new Request(ProductType.BREAD, test_distr, 2, channel);
        assertTrue(test_baker.getRequestsToMe().size() == 0);
        channel.deleteRequest(test_request);
        assertTrue(test_baker.getRequestsToMe().size() == 0);

    }
}
