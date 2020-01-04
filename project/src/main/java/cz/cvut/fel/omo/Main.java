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
        EcoSystem ecoSystem = new EcoSystem(blockChain);

        MilkFarmer milkFarmer1 = new MilkFarmer("Petya", ecoSystem);
        MilkFarmer milkFarmer2 = new MilkFarmer("Vasya", ecoSystem);
        WheatFarmer wheatFarmer = new WheatFarmer("Sam", ecoSystem);
        OrangeFarmer orangeFarmer = new OrangeFarmer("Apelsin", ecoSystem);
        Bakery bakery = new Bakery("Chlebozavod3", ecoSystem);

        Shop shop = new Shop("albert", ecoSystem);
        List<Party> parties = new ArrayList<>();
        parties.add(milkFarmer1);
        parties.add(milkFarmer2);
        parties.add(wheatFarmer);
        parties.add(orangeFarmer);
        parties.add(bakery);
        parties.add(shop);
        ecoSystem.setParties(parties);
        ecoSystem.start(50);
        System.out.println("END");
        blockChain.chain.get(287).operation.products[0].report();
//        int [][] ints = new int [3][10];
//        int [] a = new int[]{1,2,3,4,5,6,7,8,9,10};
//        int [] b = new int[]{10,20,30,40,50,60,70,80,90,100};
//        int [] c = new int[]{0,2,3,4,5,6,7,8,9,10};
//        ints[0] = a;
//        ints[1]=b;
//        ints[2] = c;
//        for (int i = 0 ; i <3; i++){
//            for (int k = 0 ; k <10; k++){
//                System.out.print(ints[i][k]+" ");
//            }
//            System.out.println("");
//        }
    }

}
