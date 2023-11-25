package org.example.predicates;

import org.example.User;
import org.example.UserPair;

import java.util.function.Predicate;

public class SigmaPredicate implements Predicate<UserPair> {
    private final double gamma;

    public SigmaPredicate(double gamma) {
        this.gamma = gamma;
    }

    //Checks whether the sigma of the two users is bigger than gamma
    @Override
    public boolean test(UserPair userPair) {
        return User.calculateSigma(userPair.getUser1(), userPair.getUser2()) > gamma;
    }
}
