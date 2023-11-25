package org.example.testGeneration;

import org.example.Transaction;
import org.example.User;

import java.util.LinkedList;

public class MoneyLaunderingUser extends RegularUser{
    private int minimalDelay;
    private int maximalDelay;
    private double provision;

    private LinkedList<Transaction> pending;

    public MoneyLaunderingUser(int id, double minTransaction, double maxTransaction, double transactionChance, int minimalDelay, int maximalDelay, double provision) {
        super(id, minTransaction, maxTransaction, transactionChance);
        this.minimalDelay = minimalDelay;
        this.maximalDelay = maximalDelay;
        this.provision = provision;
        pending = new LinkedList<>();
    }

    @Override
    public LinkedList<Transaction> simulate(int timestamp) {
        LinkedList<Transaction> resolved = new LinkedList<>();
        for(Transaction transaction : pending) {
            if(transaction.getTimestamp() == timestamp) {
                resolved.add(transaction);
            }
        }
        pending.removeAll(resolved);
        if (resolved.size() > 0) {
            return resolved;
        }
        return super.simulate(timestamp);
    }

    public void addPending(Transaction transaction, User receiver) {
        Transaction newTransaction = new Transaction(this, receiver,
                transaction.getAmount() * (1 - provision),
                transaction.getTimestamp() + (int) (Math.random() * (maximalDelay - minimalDelay) + minimalDelay));
        pending.add(newTransaction);
    }
}
