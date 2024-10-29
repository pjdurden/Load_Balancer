package com.example.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadBalancerApplication implements CommandLineRunner {

    @Autowired
    private LoadBalancer loadBalancer; // Autowire the LoadBalancer bean

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerApplication.class, args);
        System.out.println("Load Balancer is running...");
    }

    @Override
    public void run(String... args) throws Exception {
        // Start LoadBalancer listener and health checks
        loadBalancer.run();
    }
}
