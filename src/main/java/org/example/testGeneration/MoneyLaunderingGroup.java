package org.example.testGeneration;

import org.example.Transaction;
import org.example.User;

import java.util.LinkedList;

public class MoneyLaunderingGroup {
    private final LinkedList<MoneyLaunderingUser> users;
    private double chanceOfParticipation;

    public MoneyLaunderingGroup(double chanceOfParticipation) {
        this.users = new LinkedList<>();
        this.chanceOfParticipation = chanceOfParticipation;
    }

    public void addUser(MoneyLaunderingUser user) {
        users.add(user);
    }

    public Result checkGroup(LinkedList<User> suspects) {
        boolean full = true;
        int number = 0;
        for (User user: users) {
            if (suspects.contains(user)) {
                number++;
            } else {
                full = false;
            }
        }
        if (suspects.size() > users.size()) {
            full = false;
        }
        return new Result(number, full);
    }

    public LinkedList<Transaction> registerTransaction(double amount, User sender, User receiver, int timestamp) {
        LinkedList<MoneyLaunderingUser> participants = new LinkedList<>();
        while (participants.size() == 0) {
            for (MoneyLaunderingUser user : users) {
                if (Math.random() < chanceOfParticipation) {
                    participants.add(user);
                }
            }
        }
        double amountPerParticipant = amount / participants.size();
        LinkedList<Transaction> transactions = new LinkedList<>();
        for(MoneyLaunderingUser participant : participants) {
            Transaction transaction = new Transaction(sender, participant, amountPerParticipant, timestamp);
            transactions.add(transaction);
            participant.addPending(transaction, receiver);
        }
        return transactions;
    }

}
