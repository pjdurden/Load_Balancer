package com.example.loadbalancer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loadbalancer.model.Server;
import com.example.loadbalancer.sevice.ServerPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RestController
public class HealthCheck {
    private int interval;
    private final ServerPool serverPool;

    public HealthCheck(@Value("${health.check.interval}") int interval, ServerPool serverPool) {
        this.interval = interval;
        this.serverPool = serverPool;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> healthStatus = new HashMap<>();
        List<Server> servers = serverPool.getAllServers();

        for (Server server : servers) {
            boolean isReachable = isServerReachable(server);
            healthStatus.put(server.getHost() + ":" + server.getPort(), isReachable ? "healthy" : "unhealthy");
        }

        return ResponseEntity.ok(healthStatus);
    }

    public void checkServerHealth() {
        System.out.println("Checking server health at interval: " + interval + "ms");
        List<Server> servers = serverPool.getAllServers();
        for (Server server : servers) {
            boolean isReachable = isServerReachable(server);
            System.out.println("Server " + server.getHost() + ":" + server.getPort() + " is " + (isReachable ? "reachable" : "not reachable"));
        }
    }

    private boolean isServerReachable(Server server) {
        // implement reachLogic
        // for now
        return true;
        
        // try {
        //     URL url = new URL("http://www.google.com/get");
        //     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //     connection.setRequestMethod("GET");
        //     connection.setConnectTimeout(1000); // Set timeout to 1 second
        //     connection.setReadTimeout(1000);
        //     return connection.getResponseCode() == 200; // Check if the server responds with HTTP 200 OK
        // } catch (IOException e) {
        //     return false; // Server is not reachable
        // }
    }
}
