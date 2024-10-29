package com.example.loadbalancer.sevice;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.example.loadbalancer.LoadBalancer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Listener {

    private int port;
    private LoadBalancer loadBalancer;

    public Listener(int port, LoadBalancer loadBalancer) {
        this.port = port;
        this.loadBalancer = loadBalancer;
    }

    public void listen() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Listener started on port " + port);

            // Create a custom executor to handle requests
            ExecutorService executorService = Executors.newFixedThreadPool(10); // You can adjust the number of threads

            server.setExecutor(executorService); // Set the custom executor

            server.createContext("/request", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    // Only handle GET requests for simplicity
                    if ("GET".equals(exchange.getRequestMethod())) {
                        // Extract query parameter (message) from the request URL
                        String query = exchange.getRequestURI().getQuery();
                        String message = query != null ? query : "";

                        // Log the incoming request
                        System.out.println("Listener received message: " + message);

                        // Forward request to load balancer for processing
                        String responseMessage = loadBalancer.processRequest(message);

                        // Send response back to the client
                        exchange.sendResponseHeaders(200, responseMessage.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(responseMessage.getBytes());
                        os.close();
                    } else {
                        exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    }
                }
            });

            // Start the HTTP server to begin listening
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start Listener on port " + port);
            e.printStackTrace();
        }
    }
}
