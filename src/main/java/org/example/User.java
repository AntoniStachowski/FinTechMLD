package org.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

public class User {
    private LinkedList<Transaction> outgoingTransactions;
    private LinkedList<Transaction> incomingTransactions;

    private HashMap<Integer, Integer> outgoingVector;
    private HashMap<Integer, Integer> incomingVector;

    private LinkedList<User> neighbours;

    private final int id;

    private boolean visited = false;


    public User(int id) {
        this.id = id;
        outgoingTransactions = new LinkedList<>();
        incomingTransactions = new LinkedList<>();
        outgoingVector = new HashMap<>();
        incomingVector = new HashMap<>();
        neighbours = new LinkedList<>();

    }

    public void addOutgoingTransaction(Transaction transaction) {
        outgoingTransactions.add(transaction);
    }

    public void addIncomingTransaction(Transaction transaction) {
        incomingTransactions.add(transaction);
    }

    public int getId() {
        return id;
    }

    public void markFraudulent(Predicate<TransactionPair> predicate) {
        for (Transaction transaction : outgoingTransactions) {
            for (Transaction incomingTransaction : incomingTransactions) {
                if (predicate.test(new TransactionPair(transaction, incomingTransaction))) {
                    transaction.setFraudulent(true);
                    incomingTransaction.setFraudulent(true);
                }
            }
        }
    }

    public void cutProperTransactions() {
        LinkedList<Transaction> newOutgoingTransactions = new LinkedList<>();
        LinkedList<Transaction> newIncomingTransactions = new LinkedList<>();
        for (Transaction transaction : outgoingTransactions) {
            if (transaction.isFraudulent()) {
                newOutgoingTransactions.add(transaction);
                int receiverId = transaction.getReceiver().getId();
                outgoingVector.put(receiverId, outgoingVector.getOrDefault(receiverId, 0) + 1);
            }
        }
        for (Transaction transaction : incomingTransactions) {
            if (transaction.isFraudulent()) {
                newIncomingTransactions.add(transaction);
                int senderId = transaction.getSender().getId();
                incomingVector.put(senderId, incomingVector.getOrDefault(senderId, 0) + 1);
            }
        }
        outgoingTransactions = newOutgoingTransactions;
        incomingTransactions = newIncomingTransactions;
    }

    public boolean hasTransactions() {
        return !outgoingTransactions.isEmpty() || !incomingTransactions.isEmpty();
    }

    public void addNeighbour(User user) {
        neighbours.add(user);
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public LinkedList<User> getNeighbours() {
        return neighbours;
    }

    public int getInWeight() {
        return incomingTransactions.size();
    }

    public int getOutWeight() {
        return outgoingTransactions.size();
    }

    static private float scalarProduct(HashMap<Integer, Integer> h1, HashMap<Integer, Integer> h2) {
        var v1 = h1.entrySet().iterator();
        var v2 = h2.entrySet().iterator();
        var e1 = v1.next();
        var e2 = v2.next();
        int key1 = e1.getKey();
        int key2 = e2.getKey();
        int result = 0;
        if (key1 == key2) {
            result += e1.getValue() * e2.getValue();
        }
        while (v1.hasNext() || v2.hasNext()) {
            try {
                if (key1 <= key2) {
                    e1 = v1.next();
                }
                if (key1 >= key2) {
                    e2 = v2.next();
                }
            } catch (Exception e) {
                break;
            }
            key1 = e1.getKey();
            key2 = e2.getKey();
            if (key1 == key2) {
                result += e1.getValue() * e2.getValue();
            }
        }
        return result;
    }
    static public double calculateSigma(User user1, User user2) {
        var v1 = user1.incomingVector;
        var v2 = user2.incomingVector;
        var u1 = user1.outgoingVector;
        var u2 = user2.outgoingVector;
        return scalarProduct(v1, v2) * scalarProduct(u1, u2) / Math.sqrt(
                scalarProduct(v1, v1) *
                        scalarProduct(u1, u1) *
                        scalarProduct(v2, v2) *
                        scalarProduct(u2, u2)
        );
    }

    public String toString() {
        return "User " + id + "\n" +
                "Incoming transactions: " + incomingTransactions.size() + "\n" +
                "Outgoing transactions: " + outgoingTransactions.size() + "\n";
    }

}
