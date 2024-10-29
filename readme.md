# Load Balancer

## Overview

This is a simple load balancer project built with Spring Boot. The load balancer distributes incoming requests across multiple backend servers, implementing two scheduling algorithms: Round Robin and Random Selection. It's designed for robustness and can check the health of backend servers to ensure reliability.

## Features

- **Load Balancing**: Distributes requests to multiple backend servers.
- **Health Checks**: Monitors the status of backend servers.
- **Flexible Scheduling**: Choose between Round Robin and Random Selection algorithms.

## Prerequisites

Before you begin, make sure you have the following installed:

- **Java 21** (preferably OpenJDK)
- **Apache Maven 3.6.3** or later
- **Spring Boot 3.2.6** (handled via Maven)

## Setup Instructions

1. **Clone the Repository**:  
   Open a terminal and run:
   ```bash
   git clone <repository-url>
   cd Load_Balancer
   ```

2. **Configure Backend Servers**:  
   Update the `src/main/resources/application.properties` file to specify your backend servers. For example:
   ```properties
   loadbalancer.backend.servers=wikipedia.org:443,google.com:443,liftlab.com:443
   ```

3. **Build the Project**:  
   Use Maven to compile the project:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:  
   Start the Load Balancer:
   ```bash
   mvn spring-boot:run
   ```

5. **Test the Endpoints**:  
   IMP - Make sure the port is correct.
   Once the application is running, you can check the health of the backend servers by visiting:
   ```
   http://localhost:8081/health
   ```

   You can switch the load balancing algorithm by sending a POST request to:
   ```
   http://localhost:8081/api/scheduler/switch?algorithmType=round_robin
   ```

   Or switch to Random Selection:
   ```
   http://localhost:8081/api/scheduler/switch?algorithmType=random
   ```

## Additional Information

- The project uses **JUnit 5** and **Mockito** for testing. You can run the tests using:
  ```bash
  mvn test
  ```

- Health checks are performed at the interval specified in the `application.properties` file:
  ```properties
  health.check.interval=5000
  ```

## Contributing

If you want to contribute, feel free to fork the repository, make your changes, and submit a pull request. Keep it simple, keep it clean. We aim for readability and maintainability.

---