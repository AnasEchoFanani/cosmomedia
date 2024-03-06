package com.app.cosmomedia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

@SpringBootApplication
public class CosmomediaApplication {

    @Value("${server.port}")
    private String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(CosmomediaApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            logServerDetails();
            waitForExitSignal();
        };
    }

    private void logServerDetails() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        String localIpAddress = localhost.getHostAddress();

        System.out.println("Server is running on:");
        System.out.println("Localhost: http://localhost:" + serverPort);
        System.out.println("Local IP Address: http://" + localIpAddress + ":" + serverPort);
    }

    private void waitForExitSignal() {
        System.out.println("Press 'Enter' to exit...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for user input
        System.exit(0); // Exit the application
    }
}
