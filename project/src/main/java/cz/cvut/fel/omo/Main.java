package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.parties.bakery.Bakery;
import cz.cvut.fel.omo.parties.milk.MilkFarmer;
import cz.cvut.fel.omo.parties.oranges.OrangeFarmer;
import cz.cvut.fel.omo.parties.shop.Shop;
import cz.cvut.fel.omo.parties.wheat.WheatFarmer;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        BlockChain blockChain = new BlockChain();
        MilkFarmer milkFarmer1 = new MilkFarmer("Petya", blockChain);
        MilkFarmer milkFarmer2 = new MilkFarmer("Vasya", blockChain);
        WheatFarmer wheatFarmer = new WheatFarmer("Sam", blockChain);
        OrangeFarmer orangeFarmer = new OrangeFarmer("Apelsin", blockChain);
        Bakery bakery = new Bakery("Chlebozavod3", blockChain);

        Shop shop = new Shop("albert", blockChain);
        List<Party> parties = new ArrayList<>();
        parties.add(milkFarmer1);
        parties.add(milkFarmer2);
        parties.add(wheatFarmer);
        parties.add(orangeFarmer);
        parties.add(bakery);
        parties.add(shop);

        EcoSystem ecoSystem = new EcoSystem(blockChain, parties);
        ecoSystem.start(50);
    }

}
