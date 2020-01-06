package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.parties.bakery.Bakery;
import cz.cvut.fel.omo.parties.milk.MilkFarmer;
import cz.cvut.fel.omo.parties.oranges.OrangeFarmer;
import cz.cvut.fel.omo.parties.shop.ShopImpl;
import cz.cvut.fel.omo.parties.wheat.WheatFarmer;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.production.product.ProductType;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EcoSystem {
    private BlockChain blockChain;
    private int day = 0;
    Scanner in = new Scanner(System.in);
    private List<PartyImpl> parties = new ArrayList<>();
    private int partyId = 0;
    private List<Customer> customers = new ArrayList<>();
    private ShopImpl shop;

    public EcoSystem(BlockChain bc) {
        blockChain = bc;
    }

    public int getDay() {
        return day;
    }

    public List<PartyImpl> getParties() {
        return parties;
    }

    public void setParties(List<PartyImpl> parties) {
        this.parties = parties;
    }

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public void prepLounch() {
        createParty('M', "MILK FARMER");
        createParty('O', "ORANGE FARMER");
        createParty('W', "WHEAT FARMER");
        createParty('B', "BAKERY");
        createParty('S', "SHOP");
        setCustomers(5);
        startSimulation(20);
        afterActions();
    }

    public void lounch() {
        System.out.println("Welkom to FoodChain Simulation. At first define a shop. Type name of the shop");
        String shopName = in.nextLine();
        createParty('S', shopName);
        System.out.println("Configure other Parties of simulation. To add a party type a letter:" +
                "\n\'M\'- for milk farmer,\n\'W\'- for wheat farmer,\n\'O\'- for oranges farmer," +
                "\n\'B\'- for Bakery\nand after space type party`s name (e.g. \"M sam\")." +
                "\nWhen you end with parties, type \"end\"");
        while (in.hasNext()) {
            String nLine = in.nextLine();
            if (nLine.equals("end")) break;
            Pair<Character, String> parsedLine = parseInput(nLine);
            if (parsedLine == null) {
                System.err.println("Wrong input");
            } else {
                createParty(parsedLine.getKey(), parsedLine.getValue());
            }
        }
        System.out.println("Now set number of customers [0,10]");
        int num = Integer.parseInt(in.nextLine());
        if (num >= 0 && num <= 10) {
            setCustomers(num);
        } else {
            System.err.println("Wrong input");
        }
        readStepsAndStart();

    }

    private void readStepsAndStart() {
        System.out.println("Now type a number of step the simulation will do");
        int steps = in.nextInt();
        System.out.println("Simulation start");
        startSimulation(steps);
        afterActions();

    }

    private void afterActions() {
        System.out.println("\nSimulation ended. You con see reports or start simulation again.\nTo start again type \"start again\"");
        String str = in.nextLine();
        if (str.equals("start again")) readStepsAndStart();
        foodChainReport(shop.myProduction.myStorage.get(ProductType.BUN_WITH_ORANGE_JAM));

    }

    private Pair<Character, String> parseInput(String str) {
        String[] strings = str.split(" ");
        if (strings.length != 2 && strings[0].length() != 1) return null;
        char ch = strings[0].charAt(0);
        if (!Constants.TYPES.contains("" + ch)) return null;
        else {
            return new Pair<>(ch, strings[1]);
        }

    }

    public void startSimulation(int steps) {
        for (int i = 0; i < steps; ++i) {
            if (!blockChain.isSequred()) {
                System.err.println("BlockChain is not sequred");
                break;
            }
            for (PartyImpl p : parties) {
                p.checkRequestsToMe();
                p.work();
            }
            for (Customer c : customers) {
                c.act();
            }

            blockChain.sequre();
            day++;
        }
    }

    public void setCustomers(int amount) {
        if (shop == null) {
            System.err.println("MO SHOP DEFINED");
            return;
        }
        for (int i = 0; i < amount; i++) {
            customers.add(new Customer(shop));
        }
    }

    public void partiesReport() {

    }

    public void foodChainReport(Product product) {
        System.out.println("FOOD CHAIN REPORT");
        int id = product.getId();
        blockChain.getChain().stream().filter(operation -> operation.product.getId() == id).forEach(System.out::println);
    }
    void createParty(char type, String name) {
        switch (type) {
            case 'B':
                parties.add(new Bakery(name, this, partyId++));
                break;
            case 'M':
                parties.add(new MilkFarmer(name, this, partyId++));
                break;
            case 'O':
                parties.add(new OrangeFarmer(name, this, partyId++));
                break;
            case 'S':
                if (shop != null) {
                    System.err.println("Only one shop in simulation");
                } else {
                    shop = new ShopImpl(name, this, partyId++);
                    parties.add(shop);
                }
                break;
            case 'W':
                parties.add(new WheatFarmer(name, this, partyId++));
                break;
        }
    }


}
