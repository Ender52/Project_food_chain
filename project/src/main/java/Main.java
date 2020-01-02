import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        BlockChain blockChain = new BlockChain();
        MilkFarmer milkFarmer = new MilkFarmer("Petya", blockChain);
        Shop shop = new Shop("albert", blockChain);
        List<Party> parties = new ArrayList<>();
        parties.add(milkFarmer);
        parties.add(shop);
        for (int i = 0; i < 10; ++i) {
            for (Party p : parties) {
                p.checkRequestsToMe();
                p.work();
            }
        }
        System.out.println("End");
    }

}
