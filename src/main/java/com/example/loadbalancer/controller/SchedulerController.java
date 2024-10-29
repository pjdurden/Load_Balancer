package com.example.loadbalancer.controller;

import com.example.loadbalancer.sevice.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scheduler") // Base URL for the scheduler API
public class SchedulerController {

    private final Scheduler scheduler;

    @Autowired
    public SchedulerController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/switch") // Endpoint to switch the algorithm
    public ResponseEntity<String> switchAlgorithm(@RequestParam String algorithmType) {
        return scheduler.switchAlgorithm(algorithmType);
    }
}
