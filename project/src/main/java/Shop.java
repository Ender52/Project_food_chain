public class Shop extends Party {
    MilkProductionChannel milkChannel;

    Shop(String name, BlockChain bc) {
        super(name, bc);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.attend(this);
        myProduction = new ShopProduction(this);
        wallet = new Money(1000000);
    }

    @Override
    void createRequest(ProductType type, int amount) {
        if (type == ProductType.MILK) milkChannel.createRequest(type, amount, this);

    }

    @Override
    void checkRequestsToMe() {
        createRequest(ProductType.MILK, 200);

        System.out.println("Party " + name + " created request " + ProductType.MILK + " " + 200);
    }
}
