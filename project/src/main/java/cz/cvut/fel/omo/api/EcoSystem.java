package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.api.impl.BlockChainImpl;
import cz.cvut.fel.omo.api.impl.Customer;
import cz.cvut.fel.omo.api.parties.*;
import cz.cvut.fel.omo.api.product.Product;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * EcoSystem is the main class in application, it
 */
public class EcoSystem implements PartyFactory {
    private static EcoSystem INSTANCE;
    private static String info = "Initial EcoSystem class";
    private BlockChainImpl blockChain;
    private int day = 0;
    Scanner in = new Scanner(System.in);
    private List<Party> parties = new ArrayList<>();
    private int partyId = 0;
    private List<Customer> customers = new ArrayList<>();
    private Shop shop;
    private Distributor distributor;
    private boolean report = false;

    public EcoSystem(BlockChainImpl bc) {
        blockChain = bc;
    }

    /**
     * @return instance of EcoSystem
     * which is a Singleton
     */
    public synchronized static EcoSystem getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new EcoSystem(new BlockChainImpl());
        }
        return INSTANCE;
    }

    /**
     * Return a report "flag"
     * true to switch on logging , otherwise false
     *
     * @return
     */
    public boolean isReport() {
        return report;
    }


    public int getDay() {
        return day;
    }

    public List<Party> getParties() {
        return parties;
    }

    /**
     * set Parties(members) of system
     * @param parties
     */
    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public BlockChainImpl getBlockChain() {
        return blockChain;
    }

    /**
     * Lounch method, where you define all params of system in code
     */
    public void prepLounch() {

        parties.add(createParty('M', "MILK FARMER"));
        parties.add(createParty('O', "O FARMER"));
        parties.add(createParty('W', "W FARMER"));
        parties.add(createParty('B', "BER"));

        parties.add(createParty('M', "MILK FARMER2"));
        parties.add(createParty('O', "O FARMER2"));
        parties.add(createParty('W', "W FARMER2"));
        parties.add(createParty('B', "BER2"));

        parties.add(createParty('M', "MILK FARMER2"));
        parties.add(createParty('O', "O FARMER2"));
        parties.add(createParty('W', "W FARMER2"));
        parties.add(createParty('B', "BER2"));

        parties.add(createParty('S', "SHOP"));

        parties.add(createParty('D', "DISTRIBOTUR"));
        parties.add(createParty('C', "CUSTOMER1"));
        parties.add(createParty('C', "CUSTOMER2"));

        partiesReport();
        startSimulation(500);
        afterActions();
    }

    /**
     * Launch method for average user
     * where you should type params from console
     */
    public void launch() {
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
        System.out.println("Show parties report? [y / n]");
        String nLine = in.nextLine();
        if (nLine.equals("y")) partiesReport();
        readStepsAndStart();

    }

    private void readStepsAndStart() {
        System.out.println("Now type a number of step the simulation will do");
        int steps = in.nextInt();
        System.out.println("Simulation start");
        startSimulation(steps);

    }

    private void afterActions() {
        System.out.println("\nSimulation ended. You con see reports or start simulation again.\nTo start again type \"start again\"\nTo see transaction report type \"TR\"\nTo see sequrity report type \"SR\"\nTo see food chain report type \"FR\"\nTo finish type \"end\"");
        String str = in.nextLine();
        if (str.equals("start again")) readStepsAndStart();
        else if (str.equals("TR")) transactionReport();
        else if (str.equals("SR")) sequrityReport();
        else if (str.equals("FR")) foodChainReport();
        else if (str.equals("end")) return;
        afterActions();
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
            for (Party p : parties) {
                p.work();
            }
            blockChain.secure(day);
            day++;
        }
    }


    public void partiesReport() {
        report = true;
    }

    private void sequrityReport() {
        StringBuilder sb = new StringBuilder();
        List<Pair<Integer, String>> doubleSpends = blockChain.getRegulator().getReportStrings();
        List<Pair<Integer, String>> fakes = blockChain.getFaked();
//        doubleSpends.forEach(pair -> {
//            System.out.println("DAY " + pair.getKey() + "\n" + pair.getValue());
//        });
//        fakes.forEach(pair -> {
//            System.out.println("DAY " + pair.getKey() + "\n" + pair.getValue());
//        });
        var ref = new Object() {
            int cDay = 0;
        };

        while (ref.cDay <= this.day) {
            {
                sb.setLength(0);
//                sb = new StringBuilder();
                sb.append("DAY " + ref.cDay + " :\n");
                doubleSpends.stream().filter(pair -> pair.getKey() == ref.cDay).forEach(pair -> {
                    sb.append(pair.getValue() + "\n");
                });
                fakes.stream().filter(pair -> pair.getKey() == ref.cDay).forEach(pair -> {
                    sb.append(pair.getValue() + "\n");
                });
                if (!sb.toString().equals("DAY " + ref.cDay + " :\n")) System.out.println(sb.toString());
                ref.cDay++;
            }
        }

    }

    private void transactionReport() {
        System.out.println(blockChain.getTransactionReporter().getReportString());
    }

    private void foodChainReport() {
        parties.forEach(party -> {
            Storage storage = party.getMyStorage();
            for (ProductType productType : storage.getMyProducts()) {
                try {
                    if (storage.has(productType, 1)) {
                        System.out.println(showProductReport(storage.get(productType)));
                        ;
                        System.out.println();
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String showProductReport(Product product) {
        int id = product.getId();
        return "FOOD CHAIN REPORT.\nProduct #" + id + "\n" + product.report();
//        int id = product.getId();
//        System.out.println("FOOD CHAIN REPORT.\nProduct #" + id + "\n");
//        blockChain.getChain().stream().filter(operation -> operation.product.getId() == id).forEach(System.out::println);
//        if(product.getComponents().length!= 0){
//            System.out.println("Made from:\n");
//        }
    }

    @Override
    public Party createParty(char type, String name) {
        Party res = null;
        switch (type) {
            case 'B':
                res = new Bakery(name, partyId++);
                break;
            case 'M':
                res = new MilkFarmer(name, partyId++);

                break;
            case 'O':
                res = new OrangeFarmer(name, partyId++);
                break;
            case 'S':
                if (shop != null) {
                    System.err.println("Only one shop in simulation");
                } else {
                    res = new Shop(name, partyId++);

                    shop = (Shop) res;

                }
                break;
            case 'W':
                res = new WheatFarmer(name, partyId++);
                break;
            case 'D':
                if (distributor != null) {
                    System.err.println("Only one shop in simulation");
                } else {
                    res = new Distributor(name, partyId++);

                    distributor = (Distributor) res;

                }
                break;
            case 'C':
                res = new Customer(name, partyId++);

                break;
        }
        return res;
    }
}
