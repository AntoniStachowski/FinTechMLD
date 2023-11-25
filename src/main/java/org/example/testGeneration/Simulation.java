package org.example.testGeneration;

import org.example.Algorithm;
import org.example.Graph;
import org.example.Transaction;
import org.example.User;

import java.util.ArrayList;
import java.util.LinkedList;

public class Simulation {
    private ArrayList<RegularUser> users;
    private ArrayList<MoneyLaunderingGroup> groups;

    private Simulation() {
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    static public Simulation build(
            int numberOfLegitimateUsers, Range minTransaction, Range maxTransaction, Range transactionChance,
            int numberOfMoneyLaunderingUsers, int minimalDelay, int maximalDelay, Range provision, int numberOfGroups, Range participationChance,
            int numberOfMoneyLaunderingClients, Range chanceOfMoneyLaundering, Range maxLaundryAmount, Range minLaundryAmount, Range groupKnowledgeChance,
            double chanceOfFriendship) {
        Simulation simulation = new Simulation();
        simulation.generateLegitimateUsers(numberOfLegitimateUsers, minTransaction, maxTransaction, transactionChance);
        System.out.println("Legitimate users generated");
        simulation.generateMoneyLaunderingUsers(numberOfMoneyLaunderingUsers, minTransaction, maxTransaction, transactionChance, minimalDelay, maximalDelay, provision, numberOfGroups, participationChance);
        System.out.println("Money laundering users generated");
        simulation.generateMoneyLaunderingClients(numberOfMoneyLaunderingClients, minTransaction, maxTransaction, transactionChance, chanceOfMoneyLaundering, maxLaundryAmount, minLaundryAmount, groupKnowledgeChance);
        System.out.println("Money laundering clients generated");
        simulation.addFriends(chanceOfFriendship);
        System.out.println("Friends added");
        return simulation;
    }


    private void generateLegitimateUsers (int numberOfUsers, Range minTransaction, Range maxTransaction, Range transactionChance) {
        for(int i = 0; i < numberOfUsers; i++) {
            RegularUser user = new RegularUser(users.size(), minTransaction.random(), maxTransaction.random(), transactionChance.random());
            users.add(user);
        }
    }

    private void generateMoneyLaunderingUsers
            (int numberOfUsers, Range minTransaction, Range maxTransaction, Range transactionChance, int minimalDelay, int maximalDelay, Range provision, int numberOfGroups, Range particiaptionChance) {
        for (int i = 0; i < numberOfGroups; i++) {
            MoneyLaunderingGroup group = new MoneyLaunderingGroup(particiaptionChance.random());
            groups.add(group);
        }
        for(int i = 0; i < numberOfUsers; i++) {
            MoneyLaunderingUser user = new MoneyLaunderingUser(users.size(), minTransaction.random(), maxTransaction.random(), transactionChance.random(), minimalDelay, maximalDelay, provision.random());
            users.add(user);
            if(i < numberOfGroups) {
                MoneyLaunderingGroup group = groups.get(i);
                group.addUser(user);
            } else {
                int groupIndex = (int) (Math.random() * groups.size());
                MoneyLaunderingGroup group = groups.get(groupIndex);
                group.addUser(user);
            }
        }
    }

    private void generateMoneyLaunderingClients
            (int numberOfUsers, Range minTransaction, Range maxTransaction, Range transactionChance, Range chanceOfMoneyLaundering, Range maxLaundryAmount, Range minLaundryAmount, Range groupKnowledgeChance){
        for(int i = 0; i < numberOfUsers; i++) {
            MoneyLaunderingClient user = new MoneyLaunderingClient(users.size(), minTransaction.random(), maxTransaction.random(), transactionChance.random(), chanceOfMoneyLaundering.random(), maxLaundryAmount.random(), minLaundryAmount.random());
            users.add(user);
            double knowledgeChance = groupKnowledgeChance.random();
            boolean flag = false;
            while(!flag) {
                for(MoneyLaunderingGroup group : groups) {
                    if(Math.random() < knowledgeChance) {
                        user.addGroup(group);
                        flag = true;
                    }
                }
            }
        }
    }

    private void addFriends(double chanceOfFriendship){
        for (RegularUser user : users) {
            for (RegularUser friend : users) {
                if (user != friend && Math.random() < chanceOfFriendship) {
                    user.addFriend(friend);
                    friend.addFriend(user);
                }
            }
        }
    }


    public Graph simulate(int time) {
        Graph graph = new Graph();
        for (User user : users) {
            graph.addUser(user);
        }
        for (int i = 0; i < time; i++) {
            for (RegularUser user : users) {
                LinkedList< Transaction> transactions = user.simulate(i);
                if (transactions != null) {
                    for (Transaction transaction : transactions) {
                        graph.addTransaction(transaction);
                    }
                }
            }
        }
        return graph;
    }


    public Result checkGroup(LinkedList<User> users) {
        int number = 0;
        boolean found = false;
        for (MoneyLaunderingGroup group : groups) {
            Result result = group.checkGroup(users);
            number += result.getNumber();
            found = found || result.isFull();
        }
        return new Result(number, found);
    }
    public AlgorithmResults checkResults(LinkedList<LinkedList<User>> users) {
        int numberOfUsers = 0;
        int numberOfGroups = 0;
        int totalSuspects = 0;
        for (LinkedList<User> user : users) {
            totalSuspects += user.size();
            Result result = checkGroup(user);
            numberOfUsers += result.getNumber();
            if (result.isFull()) {
                numberOfGroups++;
            }
        }
        return new AlgorithmResults(numberOfUsers, numberOfGroups, totalSuspects - numberOfUsers);

    }


}
