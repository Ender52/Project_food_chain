package cz.cvut.fel.omo;

import cz.cvut.fel.omo.transactions.Transaction;

public class Block {
    Transaction myTransaction;
    String myHash;
    String previousBlockHash;

    Block(Transaction transaction, int number, String prevHash) {
        myTransaction = transaction;
        int sum = 0;
        for (char c : transaction.getSender().name.toCharArray()) sum += c;
        myHash = Integer.toString(sum + number);
        previousBlockHash = prevHash;
    }

    Block(Transaction transaction, int number) {
        myTransaction = transaction;
        int sum = 0;
        for (char c : transaction.getSender().name.toCharArray()) sum += c;
        myHash = Integer.toString(sum + number);
    }

}
