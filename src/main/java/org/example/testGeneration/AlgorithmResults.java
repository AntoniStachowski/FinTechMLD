package org.example.testGeneration;

public class AlgorithmResults {
    int numberOfUsersFound;
    int numberOfGroupsFound;

    int numberOfFalsePositives;

    public AlgorithmResults(int numberOfUsersFound, int numberOfGroupsFound, int numberOfFalsePositives) {
        this.numberOfUsersFound = numberOfUsersFound;
        this.numberOfGroupsFound = numberOfGroupsFound;
        this.numberOfFalsePositives = numberOfFalsePositives;
    }

    public int getNumberOfUsersFound() {
        return numberOfUsersFound;
    }

    public int getNumberOfGroupsFound() {
        return numberOfGroupsFound;
    }

    public int getNumberOfFalsePositives() {
        return numberOfFalsePositives;
    }

    public String toString() {
        return "AlgorithmResults(numberOfUsersFound=" + this.getNumberOfUsersFound() + ", numberOfGroupsFound=" + this.getNumberOfGroupsFound() + ", numberOfFalsePositives=" + this.getNumberOfFalsePositives() + ")";
    }
}
