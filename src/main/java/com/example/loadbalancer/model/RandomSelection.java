package com.example.loadbalancer.model;

import java.util.Random;

import com.example.loadbalancer.sevice.ServerPool;

public class RandomSelection extends SchedulingAlgorithm {

    public RandomSelection(ServerPool serverPool) {
        super(serverPool);
    }

    @Override
    public Server selectServer() {
        Random rand = new Random();
        return serverPool.getAllServers().get(rand.nextInt(serverPool.getAllServers().size()));
    }
}
