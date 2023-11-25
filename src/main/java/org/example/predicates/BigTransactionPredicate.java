package org.example.predicates;


import org.example.TransactionPair;

import java.util.function.Predicate;

public class BigTransactionPredicate implements Predicate<TransactionPair> {
    private final double alpha;
    public BigTransactionPredicate(double alpha) {
        this.alpha = alpha;
    }

    //Checks whether the first (incoming) transaction is bigger than alpha
    @Override
    public boolean test(TransactionPair transactionPair) {
        return transactionPair.getTransaction1().getAmount() > alpha;
    }
}