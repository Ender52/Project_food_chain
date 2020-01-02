import exceptions.WrongProductTypeException;

public class MilkFarmer extends Party {
    MilkProductionChannel milkChannel;

    MilkFarmer(String name, BlockChain blockChain) {
        super(name, blockChain);
        myProduction = new MilkProduction(this);
        milkChannel = blockChain.milkProductionChannel;
        milkChannel.attend(this);
        wallet = new Money(100);
    }

    @Override
    public void createRequest(ProductType type, int amount) {
    }

    @Override
    public void checkRequestsToMe() {
        boolean wasAction = false;
        for (Request r : milkChannel.allRequests) {
            if (r.productType == ProductType.MILK) {
                try {
                    if (myProduction.getMyStorage().has(ProductType.MILK, r.amount)) {
                        responseToRequest(r);
                        wasAction = true;
                        break;
                    } else {
                        startProduceProducts(r.productType, r.amount);
                        wasAction = true;
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!wasAction) startProduceProducts(ProductType.MILK, 50);
    }
}
