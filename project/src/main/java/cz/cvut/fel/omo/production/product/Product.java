package cz.cvut.fel.omo.production.product;

import cz.cvut.fel.omo.transactions.Money;

import java.util.ArrayList;
import java.util.List;

public class Product implements Reportable {
    public final ProductType type;
    //    public final Production myProduction;
    public final Money myPrice;
    private int id;
    private final Reportable[] components;
    private List<Operation> myOperations = new ArrayList<>();

    public Product(ProductType type, Product[] components, int id) {
        this.type = type;
        this.components = components;
        this.id = id;
//        myProduction = production;
        int price = 0;

        for (Product p : components) {
            price += p.myPrice.amount;
        }
        myPrice = new Money(price + 1);
    }

    public int getId() {
        return id;
    }

    public List<Operation> getMyOperations() {
        return myOperations;
    }

    @Override
    public void report() {
        for (Operation operation : myOperations) {
            System.out.println(operation.toString());
        }
        if (components.length != 0) {
            System.out.println("Was made from products:");
        }
        int i = 0;
        for (Reportable reportable : components) {
            reportable.report();
            if (i != components.length - 1) System.out.println(",");
            i++;

        }
    }

    public void addOperation(Operation operation) {
        myOperations.add(operation);
    }



}
