package org.example.testGeneration;

import org.example.Transaction;
import org.example.User;

import java.util.LinkedList;

public class MoneyLaunderingClient extends RegularUser{
    private double chanceOfMoneyLaundering;
    private double maxLaundryAmount;
    private double minLaundryAmount;

    private LinkedList<MoneyLaunderingGroup> groups;

    public MoneyLaunderingClient(int id, double minTransaction, double maxTransaction, double transactionChance, double chanceOfMoneyLaundering, double maxLaundryAmount, double minLaundryAmount) {
        super(id, minTransaction, maxTransaction, transactionChance);
        this.chanceOfMoneyLaundering = chanceOfMoneyLaundering;
        this.maxLaundryAmount = maxLaundryAmount;
        this.minLaundryAmount = minLaundryAmount;
        groups = new LinkedList<>();
    }

    public void addGroup(MoneyLaunderingGroup group) {
        groups.add(group);
    }

    @Override
    public LinkedList<Transaction> simulate(int timestamp) {
        if (Math.random() < chanceOfMoneyLaundering) {
            double amount = Math.random() * (maxLaundryAmount - minLaundryAmount) + minLaundryAmount;
            int groupIndex = (int) (Math.random() * groups.size());
            MoneyLaunderingGroup group = groups.get(groupIndex);
            User receiver = friends.get((int) (Math.random() * friends.size()));
            return group.registerTransaction(amount, this, receiver, timestamp);
        }
        return super.simulate(timestamp);
    }


}
