package com.example.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.loadbalancer.controller.HealthCheck;
import com.example.loadbalancer.model.Server;
import com.example.loadbalancer.sevice.Listener;
import com.example.loadbalancer.sevice.RequestForwarder;
import com.example.loadbalancer.sevice.Scheduler;
import com.example.loadbalancer.sevice.ServerPool;

@Component
public class LoadBalancer {

    private final Scheduler scheduler;
    private final ServerPool serverPool;
    private final Listener listener;
    private final RequestForwarder requestForwarder;
    private final HealthCheck healthCheck;

    // Inject listener port from application.properties
    @Autowired
    public LoadBalancer(@Value("${loadbalancer.listener.port}") int listenerPort,
                        Scheduler scheduler,
                        ServerPool serverPool,
                        HealthCheck healthCheck) {
        this.scheduler = scheduler;
        this.serverPool = serverPool;
        this.requestForwarder = new RequestForwarder();
        this.listener = new Listener(listenerPort, this); // Using configured listener port
        this.healthCheck = healthCheck;
    }

    // Getter for serverPool
    public ServerPool getServerPool() {
        return serverPool;
    }

    // Getter for serverPool
    public RequestForwarder getRequestForwarder() {
        return requestForwarder;
    }

    public String processRequest(String message) {
        Server selectedServer = scheduler.selectServer();
        System.out.println(selectedServer.getHost());
        String response = requestForwarder.forwardRequest(message, selectedServer);
        return response;
    }

    public void run() {
        listener.listen(); // Start listening for incoming requests
        healthCheck.checkServerHealth(); // Start health checks
    }
}
