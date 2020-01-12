package cz.cvut.fel.omo.transactions;

import cz.cvut.fel.omo.api.operations.Transaction;
import javafx.util.Pair;

import java.util.*;

/**
 * Regulator of DoubleSpending
 */
public class DoubleSpendingRegulator {
    private Map<Integer, Set<Integer>> myMap = new HashMap<>();
    private List<Pair<Integer, String>> reportStrings = new ArrayList<>();

    public List<Pair<Integer, String>> getReportStrings() {
        return reportStrings;
    }

    /**
     * Checks if transaction
     *
     * @param transaction is valid
     */
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
                reportStrings.add(new Pair<>(transaction.day, "Party " + transaction.getParty().getName() + " tried to double spend selling " + transaction.product.type));

                System.err.println("PARTY " + transaction.getParty().getName() + " TRIED TO DOUBLE SPEND");
            }
        }

    }
}
