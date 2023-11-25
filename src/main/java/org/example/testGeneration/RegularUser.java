package org.example.testGeneration;

import org.example.Transaction;

import java.util.LinkedList;

public class RegularUser extends SimulationUser{
    private double minTransaction;
    private double maxTransaction;
    protected LinkedList<SimulationUser> friends;

    private double transactionChance;

    public RegularUser(int id, double minTransaction, double maxTransaction, double transactionChance) {
        super(id);
        this.minTransaction = minTransaction;
        this.maxTransaction = maxTransaction;
        this.transactionChance = transactionChance;
        friends = new LinkedList<>();
    }

    public void addFriend(SimulationUser friend) {
        friends.add(friend);
    }

    @Override
    public LinkedList<Transaction> simulate(int timestamp) {
        if (Math.random() < transactionChance) {
            double amount = Math.random() * (maxTransaction - minTransaction) + minTransaction;
            int friendIndex = (int) (Math.random() * friends.size());
            SimulationUser friend = friends.get(friendIndex);
            Transaction transaction = new Transaction(this, friend, amount, timestamp);
            return new LinkedList<Transaction>() {{
                add(transaction);
            }};
        }
        return null;
    }

}
