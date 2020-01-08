package cz.cvut.fel.omo;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        EcoSystem ecoSystem = new EcoSystem(new BlockChain());
        ecoSystem.prepLounch();
//        ecoSystem.launch();

    }

}
