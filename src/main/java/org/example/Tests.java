package org.example;

import org.example.testGeneration.Range;
import org.example.testGeneration.Simulation;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class Tests {
    @Test
    public void testSmall() {
        Simulation simulation = Simulation.build(
                3,
                new Range(10, 200),
                new Range(500, 3000),
                new Range(0, 0),
                3,
                1,
                3,
                new Range(0, 0.05),
                1,
                new Range(0.9, 0.9),
                1,
                new Range(0.3, 0.3),
                new Range(5000, 6000),
                new Range(15000, 35000),
                new Range(1, 1),
                0.5

        );

        Graph g = simulation.simulate(100);
        System.out.println("Done generating");


        Algorithm algorithm = new Algorithm(3, 2000, 0.95, 0.2, 1);
        LinkedList<LinkedList<User>> clusters = algorithm.run(g);

        System.out.println("Clusters:");
        for (LinkedList<User> cluster : clusters) {
            System.out.println(cluster);
        }

        System.out.println(simulation.checkResults(clusters));
    }

    @Test
    public void mediumTest() {
        Simulation simulation = Simulation.build(
                4000,
                new Range(10, 200),
                new Range(500, 3000),
                new Range(0.01, 0.4),
                200,
                1,
                3,
                new Range(0, 0.05),
                40,
                new Range(0.7, 0.9),
                70,
                new Range(0.04, 0.2),
                new Range(10000, 15000),
                new Range(30000, 75000),
                new Range(0.9, 0.95),
                0.1
        );
        Graph g = simulation.simulate(600);
        System.out.println("Done generating");

        Algorithm algorithm = new Algorithm(3, 2000, 0.95, 0.2, 3);
        LinkedList<LinkedList<User>> clusters = algorithm.run(g);

        System.out.println(simulation.checkResults(clusters));
    }

    @Test
    public void largeTest() {
        Simulation simulation = Simulation.build(
                50000,
                new Range(10, 200),
                new Range(500, 3000),
                new Range(0.01, 0.1),
                1000,
                1,
                3,
                new Range(0, 0.05),
                40,
                new Range(1, 1),
                100,
                new Range(0.03, 0.15),
                new Range(50000, 70000),
                new Range(100000, 350000),
                new Range(0.9, 0.95),
                0.01
        );
        Graph g = simulation.simulate(600);
        System.out.println("Done generating");

        Algorithm algorithm = new Algorithm(3, 2000, 0.95, 0.2, 1.8);
        LinkedList<LinkedList<User>> clusters = algorithm.run(g);

        System.out.println(simulation.checkResults(clusters));
    }

}
