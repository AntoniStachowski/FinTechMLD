package org.example.predicates;

import org.example.User;

import java.util.function.Predicate;

public class SuspiciousUserPredicate implements Predicate<User> {
    private final double q;

    public SuspiciousUserPredicate(double q) {
        this.q = q;
    }

    //Checks whether the B-value of the user is bigger than q
    @Override
    public boolean test(User user) {
        double in = user.getInWeight();
        double out = user.getOutWeight();
        if (in == 0 || out == 0) {
            return false;
        }
        //Calculate the B-value as described in the paper
        double bValue = 2 * in * out / (in * in + out * out);
        bValue *= Math.log(Math.max(Math.min(in, out), 1));
        return bValue > q;
    }
}
