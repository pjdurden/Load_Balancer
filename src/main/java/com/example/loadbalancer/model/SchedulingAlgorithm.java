package com.example.loadbalancer.model;

import com.example.loadbalancer.sevice.ServerPool;

public abstract class SchedulingAlgorithm {
    protected ServerPool serverPool;

    public SchedulingAlgorithm(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    public abstract Server selectServer();
}
