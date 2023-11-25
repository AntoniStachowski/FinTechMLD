package org.example.testGeneration;

public class Range {
    private final double min;
    private final double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double random() {
        return min + Math.random() * (max - min);
    }
}
