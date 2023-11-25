package org.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

public class Graph {
    private HashMap<Integer, User> users;
    private final LinkedList<User> fraudulentUsers;

    public Graph() {
        users = new HashMap<>();
        fraudulentUsers = new LinkedList<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addTransaction(Transaction transaction) {
        users.get(transaction.getSender().getId()).addOutgoingTransaction(transaction);
        users.get(transaction.getReceiver().getId()).addIncomingTransaction(transaction);
    }

    public void markFraudulent(Predicate<TransactionPair> predicate) {
        for (User user : users.values()) {
            user.markFraudulent(predicate);
        }
    }

    public void cutProperTransactions() {
        for (User user : users.values()) {
            user.cutProperTransactions();
        }
    }

    public void cutProperUsers() {
        HashMap<Integer, User> newUsers = new HashMap<>();
        for (User user : users.values()) {
            if (user.hasTransactions()) {
                newUsers.put(user.getId(), user);
            }
        }
        users = newUsers;
    }

    public void markFraudulentUsers(Predicate<User> predicate) {
        for (User user : users.values()) {
            if (predicate.test(user)) {
                fraudulentUsers.add(user);
            }
        }
    }

    public void connectLaunderingGroups (Predicate<UserPair> predicate) {
        for (User user1: fraudulentUsers) {
            for (User user2: fraudulentUsers) {
                if (predicate.test(new UserPair(user1, user2))) {
                    user1.addNeighbour(user2);
                    user2.addNeighbour(user1);
                }
            }
        }
    }

    public LinkedList<LinkedList<User>> getLaunderingGroups() {
        LinkedList<LinkedList<User>> launderingGroups = new LinkedList<>();
        for (User user : fraudulentUsers) {
            if (!user.isVisited()) {
                LinkedList<User> launderingGroup = new LinkedList<>();
                launderingGroup.add(user);
                user.setVisited(true);
                LinkedList<User> queue = new LinkedList<>();
                queue.add(user);
                while (!queue.isEmpty()) {
                    User currentUser = queue.poll();
                    for (User neighbour : currentUser.getNeighbours()) {
                        if (!neighbour.isVisited()) {
                            launderingGroup.add(neighbour);
                            neighbour.setVisited(true);
                            queue.add(neighbour);
                        }
                    }
                }
                launderingGroups.add(launderingGroup);
            }
        }
        return launderingGroups;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users.values()) {
            stringBuilder.append(user.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
