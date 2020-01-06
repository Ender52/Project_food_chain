package cz.cvut.fel.omo;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        BlockChain blockChain = new BlockChain();
        EcoSystem ecoSystem = new EcoSystem(blockChain);
        ecoSystem.createParty('M', "Petya");
        ecoSystem.createParty('M', "Vasya");
        ecoSystem.createParty('W', "Sam");
        ecoSystem.createParty('O', "Apelsin");
        ecoSystem.createParty('B', "Chlebozavod3");
        ecoSystem.createParty('S', "ALBERT");
        ecoSystem.start(50);
        System.out.println("END");

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
