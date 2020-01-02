import exceptions.WrongProductTypeException;

public abstract class Party {
    public final String name;
    protected Production myProduction;
    BlockChain blockChain;
    Money wallet;


    Party(String name, BlockChain bc) {
        System.out.println("Party " + name + " created");
        this.name = name;
        this.blockChain = bc;
    }


    abstract void createRequest(ProductType type, int amount);

    abstract void checkRequestsToMe();

    public void work() {
        if (myProduction != null) myProduction.produce();
    }

    public void startProduceProducts(ProductType type, int amount) {
        myProduction.startProducingProducts(type, amount);
    }

    public void buyProducts(ProductType type, int amount) {
        createRequest(type, amount);

    }


    public void responseToRequest(Request request) {
        System.out.println("Party " + name + " is responding on request from Party " + request.sender.name);
        Product[] products = myProduction.getMyStorage().getProducts(request.productType, request.amount);
        Transaction transaction = new Transaction(this, request.sender, products);
        request.channel.doTransaction(transaction);
        request.channel.allRequests.remove(request);
    }

    public void receiveProducts(Product[] products) {
        {
            try {
                myProduction.getMyStorage().put(products);
                System.out.println("Party " + name + " received " + products.length + " " + products[0].type);
            } catch (WrongProductTypeException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeBalance(int amount) {
        System.out.println("Party " + name + " balance chenge " + amount);
        wallet.add(amount);
    }
}
