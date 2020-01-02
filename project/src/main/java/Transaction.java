public class Transaction {
    private Party sender;
    private Party receiver;
    private Product[] products;


    Transaction(Party sender, Party receiver, Product[] products) {
        this.sender = sender;
        this.receiver = receiver;
        this.products = products;
    }

    public Party getSender() {
        return sender;
    }

    public Party getReceiver() {
        return receiver;
    }

    public Product[] getProducts() {
        return products;
    }

}
