public class Request {

    public final ProductType productType;
    public final Party sender;
    public final int amount;
    public final AbstractChannel channel;

    Request(ProductType pType, Party sender, int amount, AbstractChannel channel) {
        productType = pType;
        this.sender = sender;
        this.amount = amount;
        this.channel = channel;
    }
}
