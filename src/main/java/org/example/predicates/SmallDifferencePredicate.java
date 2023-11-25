package org.example.predicates;

import org.example.TransactionPair;

import java.util.function.Predicate;

public class SmallDifferencePredicate implements Predicate<TransactionPair> {
    private final double beta;

    public SmallDifferencePredicate(double beta) {
        this.beta = beta;
    }

    //Checks whether the relative difference between the two transactions is smaller than beta
    @Override
    public boolean test(TransactionPair transactionPair) {
        return transactionPair.getTransaction1().getAmount() >= transactionPair.getTransaction2().getAmount() &&
                transactionPair.getTransaction1().getAmount() * beta < transactionPair.getTransaction2().getAmount();
    }
}