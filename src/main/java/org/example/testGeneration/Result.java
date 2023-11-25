package org.example.testGeneration;

public class Result {
    private final int number;
    private final boolean full;

    public Result(int number, boolean full) {
        this.number = number;
        this.full = full;
    }

    public int getNumber() {
        return number;
    }

    public boolean isFull() {
        return full;
    }
}
