package com.example.loadbalancer.sevice;

import org.springframework.stereotype.Component;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.example.loadbalancer.model.Server;
 

@Component
public class RequestForwarder {
    private final HttpClient httpClient;

    public RequestForwarder() {
        this.httpClient = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(2))
                                    .build();
    }

    public String forwardRequest(String request, Server server) {
        try {
            // Encode the request message
            // String encodedMessage = URLEncoder.encode(request, StandardCharsets.UTF_8.toString());

            // // Construct the HTTPS URL
            // String url = "https://" + server.getHost() + ":" + server.getPort() + "/response?message=" + encodedMessage;
            String url = "https://" + server.getHost() + ":" + server.getPort();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(2))
                .GET()
                .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Request failed";
        }
    }
}