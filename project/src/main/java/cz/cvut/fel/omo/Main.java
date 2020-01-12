package cz.cvut.fel.omo;

import cz.cvut.fel.omo.api.EcoSystem;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        EcoSystem.getInstance().prepLounch();
//        EcoSystem.getInstance().launch();

    }

}
