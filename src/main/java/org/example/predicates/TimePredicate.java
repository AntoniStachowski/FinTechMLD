package org.example.predicates;

import org.example.TransactionPair;

import java.util.function.Predicate;

public class TimePredicate implements Predicate<TransactionPair> {
    private final int theta;

    public TimePredicate(int theta) {
        this.theta = theta;
    }

    //Checks whether the time difference between the two transactions is smaller than theta
    @Override
    public boolean test(TransactionPair transactionPair) {
        return transactionPair.getTransaction2().getTimestamp() - transactionPair.getTransaction1().getTimestamp() <= theta;
    }
}