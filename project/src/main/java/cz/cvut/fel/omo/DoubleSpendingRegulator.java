package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.PartyImpl;
import cz.cvut.fel.omo.transactions.Transaction;

import java.util.*;

public class DoubleSpendingRegulator {
    private Map<Integer, Set<Integer>> myMap = new HashMap<>();
    private List<PartyImpl> violators = new ArrayList<>();

    public void validateTransaction(Transaction transaction) {
        int id = transaction.getParty().getId();

        int newId = transaction.product.getId();

        if (!myMap.containsKey(id)) {
            Set<Integer> newSet = new HashSet<>();
            newSet.add(newId);
            myMap.put(id, newSet);
        } else {
            Set<Integer> set = myMap.get(id);
            if (!set.add(newId)) {
                violators.add(transaction.getParty());
                System.err.println("PARTY " + transaction.getParty().name + " TRIED TO DOUBLE SPEND");
            }
        }

    }
}
