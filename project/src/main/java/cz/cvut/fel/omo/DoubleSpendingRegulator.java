package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.production.product.Product;
import cz.cvut.fel.omo.transactions.Transaction;

import java.util.*;

public class DoubleSpendingRegulator {
    private Map<Integer, Set<Integer>> myMap = new HashMap<>();
    private List<Party> violators = new ArrayList<>();

    public void validateTransaction(Transaction transaction) {
        int id = transaction.getParty().getId();

        Set<Integer> ids = new HashSet<>();
        for (Product p : transaction.products) {
            if (!ids.add(p.getId())) {
                violators.add(transaction.getParty());
                System.err.println("PARTY " + transaction.getParty().name + " TRIED TO DOUBLE SPEND");
            }
        }
        if (!myMap.containsKey(id)) {
            myMap.put(id, ids);
        } else {
            Set<Integer> set = myMap.get(id);
            ids.forEach(i -> {
                if (!set.add(i)) {
                    violators.add(transaction.getParty());
                    System.err.println("PARTY " + transaction.getParty().name + " TRIED TO DOUBLE SPEND");
                }
            });
        }
    }
}
