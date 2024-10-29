package com.example.loadbalancer.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.loadbalancer.model.RandomSelection;
import com.example.loadbalancer.model.RoundRobin;
import com.example.loadbalancer.model.SchedulingAlgorithm;
import com.example.loadbalancer.model.Server;

@Component
public class Scheduler {

    private SchedulingAlgorithm schedulingAlgorithm;
    private final ServerPool serverPool;

    @Autowired
    public Scheduler(ServerPool serverPool) {
        this.serverPool = serverPool;
        switchAlgorithm("round_robin"); // Initialize with round robin
    }

    public Server selectServer() {
        return schedulingAlgorithm.selectServer();
    }

    // Method to switch algorithm
    public ResponseEntity<String> switchAlgorithm(String algorithmType) {
        switch (algorithmType) {
            case "round_robin":
                setSchedulingAlgorithm(new RoundRobin(serverPool));
                return ResponseEntity.ok("Switched to Round Robin algorithm.");
            case "random":
                setSchedulingAlgorithm(new RandomSelection(serverPool));
                return ResponseEntity.ok("Switched to Random Selection algorithm.");
            default:
                return ResponseEntity.badRequest().body("Invalid algorithm type.");
        }
    }

    private void setSchedulingAlgorithm(SchedulingAlgorithm schedulingAlgorithm) {
        this.schedulingAlgorithm = schedulingAlgorithm;
    }
}
