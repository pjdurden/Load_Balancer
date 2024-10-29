package com.example.loadbalancer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loadbalancer.controller.HealthCheck;
import com.example.loadbalancer.model.Server;
import com.example.loadbalancer.sevice.Scheduler;
import com.example.loadbalancer.sevice.ServerPool;

import static org.mockito.Mockito.*;

public class LoadBalancerTest {

    @Mock
    private Scheduler scheduler; // Mocked dependency

    @Mock
    private ServerPool serverPool; // Mocked dependency

    @Mock
    private HealthCheck healthCheck; // Mocked dependency

    // Create an instance variable for LoadBalancer
    private LoadBalancer loadBalancer;

    private final int listenerPort = 8080; // Example port

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Explicitly set up the LoadBalancer instance with mocked dependencies
        loadBalancer = new LoadBalancer(listenerPort, scheduler, serverPool, healthCheck);
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        String message = "Test request";
        Server mockServer = mock(Server.class);
        when(scheduler.selectServer()).thenReturn(mockServer);
        when(mockServer.getHost()).thenReturn("localhost");

        // Act
        String response = loadBalancer.processRequest(message);

        // Assert
        verify(scheduler).selectServer(); // Verify that the server was selected
        verify(mockServer, times(2)).getHost(); // Verify that the host was accessed two times
        // Add more assertions based on the response if needed
    }

    @Test
    public void testRun() {
        // Act
        loadBalancer.run();

        // Assert
        verify(healthCheck).checkServerHealth(); // Ensure health checks were invoked
        // You can add more verification based on your implementation
    }
}
