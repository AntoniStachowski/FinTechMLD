package org.example;

public class TransactionPair {
    private final Transaction transaction1;
    private final Transaction transaction2;

    public TransactionPair(Transaction transaction1, Transaction transaction2) {
        this.transaction1 = transaction1;
        this.transaction2 = transaction2;
    }

    public Transaction getTransaction1() {
        return transaction1;
    }

    public Transaction getTransaction2() {
        return transaction2;
    }
}
