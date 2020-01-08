package cz.cvut.fel.omo;

import cz.cvut.fel.omo.api.Party;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.Storage;
import cz.cvut.fel.omo.api.impl.ShopImpl;
import cz.cvut.fel.omo.api.parties.Bakery;
import cz.cvut.fel.omo.api.parties.MilkFarmer;
import cz.cvut.fel.omo.api.parties.OrangeFarmer;
import cz.cvut.fel.omo.api.parties.WheatFarmer;
import cz.cvut.fel.omo.exceptions.WrongProductTypeException;
import cz.cvut.fel.omo.production.product.Product;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EcoSystem {
    private static EcoSystem INSTANCE;
    private static String info = "Initial EcoSystem class";
    private BlockChain blockChain;
    private int day = 0;
    Scanner in = new Scanner(System.in);
    private List<Party> parties = new ArrayList<>();
    private int partyId = 0;
    private List<Customer> customers = new ArrayList<>();
    private ShopImpl shop;
    private boolean report = false;

    public EcoSystem(BlockChain bc) {
        blockChain = bc;
    }

    public synchronized static EcoSystem getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new EcoSystem(new BlockChain());
        }
        return INSTANCE;
    }

    public int getDay() {
        return day;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
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

        createParty('M', "MILK FARMER2");
        createParty('O', "ORANGE FARMER2");
        createParty('W', "WHEAT FARMER2");
        createParty('B', "BAKERY2");

        createParty('M', "MILK FARMER3");
        createParty('O', "ORANGE FARMER3");
        createParty('W', "WHEAT FARMER3");
        createParty('B', "BAKERY3TR");

        createParty('S', "SHOP");
        setCustomers(10);
        startSimulation(100);
        afterActions();
    }

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
                p.checkRequestsToMe();
                p.work();
            }
            for (Customer c : customers) {
                c.act();
            }

            blockChain.secure(day);
            day++;
        }
    }

    public void setCustomers(int amount) {
        if (shop == null) {
            System.err.println("NO SHOP DEFINED");
            return;
        }
        for (int i = 0; i < amount; i++) {
            customers.add(new Customer(shop));
        }
    }

    public void partiesReport() {
        report = true;
    }

    private void sequrityReport() {
        StringBuilder sb = new StringBuilder();
        List<Pair<Integer, String>> doubleSpends = blockChain.getRegulator().getReportStrings();
        List<Pair<Integer, String>> fakes = blockChain.getFaked();
        doubleSpends.forEach(pair -> {
            System.out.println("DAY " + pair.getKey() + "\n" + pair.getValue());
        });
        fakes.forEach(pair -> {
            System.out.println("DAY " + pair.getKey() + "\n" + pair.getValue());
        });
        var ref = new Object() {
            int cDay = 0;
        };

        while (ref.cDay <= this.day) {
            {
                boolean typed = false;
                sb.append("DAY " + day + " :\n");
                doubleSpends.stream().filter(pair -> pair.getKey() == ref.cDay).forEach(pair -> {
                    sb.append(pair.getValue() + "\n");
                });
                fakes.stream().filter(pair -> pair.getKey() == ref.cDay).forEach(pair -> {
                    sb.append(pair.getValue() + "\n");
                });
//            if(sb.toString().equals("DAY "+ day+ " :\n")) sb.
//            ref.cDay++;
            }
        }
//        int day =0;
//        int doubleSpendsCounter= 0;
//        int fakesCounter= 0;
//        Pair<Operation, Integer> pairF = blockChain.getFaked().get(fakesCounter);
//        Pair<Integer, String> pairD = doubleSpends.get(doubleSpendsCounter);
//        boolean dayTyped= false;
//        while (day <= this.day){
//            if(pairF.getValue() != day && pairD.getKey() != day) {
//                day++;
//                dayTyped = false;
//                continue;
//            }else {
//                if(!dayTyped){
//                    sb.append("DAY ");
//                    sb.append(day);
//                    sb.append(" :\n");
//                    dayTyped = true;
//                }
//                if (pairF.getValue() == day) {
//                    sb.append("Somebody tried to fake block with operation: ");
//                    sb.append(pairF.getKey().toString());
//                    sb.append("\n");
//                    if(fakesCounter <= blockChain.getFaked().size()-2) fakesCounter++;
//                    if(fakesCounter == blockChain.getFaked().size()-1) break;
//                    pairF = blockChain.getFaked().get(fakesCounter);
//                }
//                if (pairD.getKey() == day) {
//                    sb.append(pairD.getValue());
//                    sb.append("\n");
//                    if(doubleSpendsCounter <= blockChain.getRegulator().getReportStrings().size()-2) {
//                        doubleSpendsCounter++;
//                    }
//                    if(doubleSpendsCounter == blockChain.getRegulator().getReportStrings().size()-1) { break;}
//                    pairD = doubleSpends.get(doubleSpendsCounter);
//                }
//            }
//
//        }
//        System.out.println(sb.toString());


    }

    private void transactionReport() {
        System.out.println(blockChain.getTransactionReporter().getReportString());
    }

    public void foodChainReport() {
        parties.forEach(party -> {
            Storage storage = party.getMyProduction().getMyStorage();
            for (ProductType productType : storage.getMyProducts()) {
                try {
                    if (storage.has(productType, 1)) {
                        showProductReport(storage.get(productType));
                        System.out.println();
                    }
                } catch (WrongProductTypeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showProductReport(Product product) {
        int id = product.getId();
        System.out.println("FOOD CHAIN REPORT.\nProduct #" + id + "\n");
        blockChain.getChain().stream().filter(operation -> operation.product.getId() == id).forEach(System.out::println);
    }


    void createParty(char type, String name) {
        switch (type) {
            case 'B':
                parties.add(new Bakery(name, partyId++));
                break;
            case 'M':
                parties.add(new MilkFarmer(name, partyId++));
                break;
            case 'O':
                parties.add(new OrangeFarmer(name, partyId++));
                break;
            case 'S':
                if (shop != null) {
                    System.err.println("Only one shop in simulation");
                } else {
                    shop = new ShopImpl(name, partyId++);
                    parties.add(shop);
                }
                break;
            case 'W':
                parties.add(new WheatFarmer(name,  partyId++));
                break;
        }
    }
}
