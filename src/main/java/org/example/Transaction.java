package org.example;

public class Transaction {
    private final User sender;
    private final User receiver;

    private final double amount;
    private final int timestamp;

    private boolean isFraudulent;

    public Transaction(User sender, User receiver, double amount, int timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = timestamp;
        this.isFraudulent = false;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public boolean isFraudulent() {
        return isFraudulent;
    }

    public void setFraudulent(boolean isFraudulent) {
        this.isFraudulent = isFraudulent;
    }

    public String toString() {
        return "Transaction(sender=" + this.getSender().getId() + ", receiver=" + this.getReceiver().getId() + ", amount=" + this.getAmount() + ", timestamp=" + this.getTimestamp() + ", isFraudulent=" + this.isFraudulent() + ")";
    }
}
