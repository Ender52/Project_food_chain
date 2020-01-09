package cz.cvut.fel.omo.production.product;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.transactions.Money;

import java.util.List;

public class Product implements Reportable {
    public final ProductType type;
    //    public final Production myProduction;
    public final Money myPrice;
    private int id;
    private final Reportable[] components;

    public Product(ProductType type, Product[] components, int id) {
        this.type = type;
        this.components = components;
        this.id = id;
        int price = 0;

        for (Product p : components) {
            price += p.myPrice.amount;
        }
        myPrice = new Money(price + 10);
    }

    public Reportable[] getComponents() {
        return components;
    }

    public int getId() {
        return id;
    }

    @Override
    public void report() {
        List<Operation> chain = EcoSystem.getInstance().getBlockChain().getChain();
        chain.stream().filter(operation -> operation.product.getId() == id).forEach(System.out::println);
        if (components.length != 0) {
            System.out.println("Made from:");
            for (Reportable reportable : components) reportable.report();
        }
//        for (Operation operation : myOperations) {
//            System.out.println(operation.toString());
//        }
//        if (components.length != 0) {
//            System.out.println("Was made from products:");
//        }
//        int i = 0;
//        for (Reportable reportable : components) {
//            reportable.report();
//            if (i != components.length - 1) System.out.println(",");
//            i++;
//
//        }
    }




}
