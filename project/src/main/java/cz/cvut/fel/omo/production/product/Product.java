package cz.cvut.fel.omo.production.product;

import cz.cvut.fel.omo.EcoSystem;
import cz.cvut.fel.omo.api.ProductType;
import cz.cvut.fel.omo.api.impl.ReportVisitor;
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
    public String report() {
        List<Operation> chain = EcoSystem.getInstance().getBlockChain().getChain();
        ReportVisitor visitor = new ReportVisitor();
        chain.stream().filter(operation -> operation.product.getId() == id).forEach(operation -> operation.accept(visitor));
        if (components.length != 0) {
            visitor.getStringBuilder().append("Made from:\n");
            for (Reportable reportable : components) visitor.getStringBuilder().append(reportable.report());
        }
        return visitor.getStringBuilder().toString();
    }




}
