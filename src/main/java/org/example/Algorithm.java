package org.example;

import org.example.predicates.*;

import java.util.LinkedList;
import java.util.function.Predicate;

public class Algorithm {
    private final int theta;
    private final double alpha;
    private final double beta;
    private final double gamma;
    private final double q;

    public Algorithm(int theta, double alpha, double beta, double gamma, double q) {
        this.theta = theta;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.q = q;
    }

    public LinkedList<LinkedList<User>> run (Graph g) {
        Predicate<TransactionPair> predicate = new BigTransactionPredicate(alpha).and(new SmallDifferencePredicate(beta)).and(new TimePredicate(theta));
        g.markFraudulent(predicate);
        g.cutProperTransactions();
        g.cutProperUsers();
        Predicate<User> userPredicate = new SuspiciousUserPredicate(q);
        g.markFraudulentUsers(userPredicate);
        Predicate<UserPair> userPairPredicate = new SigmaPredicate(gamma);
        g.connectLaunderingGroups(userPairPredicate);
        return g.getLaunderingGroups();
    }
}
