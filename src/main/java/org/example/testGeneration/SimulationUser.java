package org.example.testGeneration;

import org.example.Transaction;
import org.example.User;

import java.util.LinkedList;

public abstract class SimulationUser extends User {


    public SimulationUser(int id) {
        super(id);
    }

    public abstract LinkedList<Transaction> simulate(int timestamp);
}
