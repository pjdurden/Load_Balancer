package com.example.loadbalancer.sevice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.loadbalancer.model.Server;

import javax.annotation.PostConstruct;


import java.util.ArrayList;
import java.util.List;

@Component
public class ServerPool {

    private final List<Server> servers = new ArrayList<>();

    // Inject the backend server hosts from application.properties
    @Value("${loadbalancer.backend.servers}")
    private String backendServers;
        
    @PostConstruct
    private void initializeServers() {
        // System.out.println("Backend servers configuration: " + backendServers);
        
        if (backendServers != null && !backendServers.isEmpty()) {
            String[] serversArray = backendServers.split(",");
            for (String serverInfo : serversArray) {
                String[] parts = serverInfo.split(":");
                if (parts.length == 2) {
                    String host = parts[0].trim();
                    int port = Integer.parseInt(parts[1].trim());
                    servers.add(new Server(host, port)); // Create and add new server
                    System.out.println("Added server: " + host + ":" + port);
                } else {
                    System.err.println("Invalid server info format: " + serverInfo);
                }
            }
        } else {
            System.err.println("No backend servers configured.");
        }
    }  

    public List<Server> getAllServers() {
        return servers;
    }

    public void addServer(Server server) {
        servers.add(server); // Method to add a new server
    }
}
