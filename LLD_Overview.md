# Low-Level Design Overview for Load Balancer

## Introduction

This document outlines the low-level design of the Load Balancer, detailing the architecture and the responsibilities of various components. The Load Balancer is designed to efficiently distribute incoming requests across multiple backend servers, ensuring high availability and performance. The design adheres to SOLID principles, aiming for clarity and maintainability.

## Architecture Overview

```
+----------------+    +-----------------+    +--------------------+
|  LoadBalancer  |<-->|    Scheduler    |<-->|  SchedulingAlgorithm|
+----------------+    +-----------------+    +--------------------+
| - listener      |    | - serverPool    |    | - selectServer()   |
| - serverPool    |    | - schedulingAlg  |    +--------------------+
| - requestForwarder|   | + selectServer() |
| + processRequest()|   | + switchAlgorithm()|
+----------------+    +-----------------+
           |                    |
           v                    v
+----------------+    +----------------+
|    Listener     |    |   ServerPool   |
+----------------+    +----------------+
| - port          |    | - servers      |
| + listen()      |    | + addServer()  |
+----------------+    | + getAllServers()|
                     +----------------+
```

### Components

1. **LoadBalancer**
   - **Responsibilities:**
     - Initializes and manages core components, including the listener and request forwarder.
     - Processes incoming requests and forwards them to the selected backend server.
   - **Key Methods:**
     - `processRequest(String message)`: Handles incoming requests and delegates them to the `RequestForwarder`.

2. **Listener**
   - **Responsibilities:**
     - Listens for incoming HTTP requests on a specified port.
     - Routes requests to the `LoadBalancer` for processing.
   - **Key Methods:**
     - `listen()`: Starts the HTTP server and handles incoming requests.

3. **Scheduler**
   - **Responsibilities:**
     - Manages the scheduling algorithm used to select the backend server.
     - Provides an endpoint to switch between different load-balancing algorithms.
   - **Key Methods:**
     - `selectServer()`: Retrieves the next server based on the active scheduling algorithm.
     - `switchAlgorithm(String algorithmType)`: Changes the load-balancing algorithm at runtime.

4. **SchedulingAlgorithm**
   - **Responsibilities:**
     - Abstract class for defining scheduling strategies (e.g., Round Robin, Random Selection).
   - **Key Methods:**
     - `selectServer()`: Must be implemented by subclasses to return a selected server.

5. **ServerPool**
   - **Responsibilities:**
     - Maintains a list of available backend servers.
     - Provides methods to add and retrieve servers.
   - **Key Methods:**
     - `getAllServers()`: Returns the list of servers for load balancing.
     - `addServer(Server server)`: Adds a new server to the pool.

6. **RequestForwarder**
   - **Responsibilities:**
     - Forwards requests to the selected backend server and retrieves responses.
   - **Key Methods:**
     - `forwardRequest(String request, Server server)`: Sends the request to the specified server and returns the response.

## SOLID Principles

- **Single Responsibility Principle**: Each class is designed with a specific responsibility, promoting clarity and maintainability.
- **Open/Closed Principle**: The design allows for the addition of new scheduling algorithms without modifying existing code, achieved by extending the `SchedulingAlgorithm` class.

## Error Handling

Error handling is implemented throughout the code, particularly in the `RequestForwarder`, where network errors and response failures are logged. This ensures that the Load Balancer remains robust and provides meaningful error messages.

## Testing

Unit tests will be written to validate the functionality of each component, ensuring that the Load Balancer operates as intended. Currently, unit tests have been implemented for the Load Balancer class.

## Conclusion

This low-level design aims to deliver a scalable and maintainable Load Balancer capable of efficiently distributing requests among backend servers. By adhering to best practices and principles, the system is built to accommodate future enhancements without complicating the existing architecture.

--- 