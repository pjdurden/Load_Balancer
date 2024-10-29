package com.example.loadbalancer.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.example.loadbalancer.sevice.ServerPool;

public class RoundRobin extends SchedulingAlgorithm {
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public RoundRobin(ServerPool serverPool) {
        super(serverPool);
    }

    @Override
    public Server selectServer() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % serverPool.getAllServers().size());
        return serverPool.getAllServers().get(index);
    }
}
