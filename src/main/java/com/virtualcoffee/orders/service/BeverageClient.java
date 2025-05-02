package com.virtualcoffee.orders.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeverageClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8000/menu";

    public boolean isAvailable(String name) {
        try {
            ResponseEntity<Beverage[]> response = restTemplate.getForEntity(BASE_URL, Beverage[].class);
            if (response.getBody() == null) return false;

            return Arrays.stream(Objects.requireNonNull(response.getBody()))
                         .anyMatch(b -> b.getName().equalsIgnoreCase(name));
        } catch (Exception e) {
            System.err.println("Error fetching beverages: " + e.getMessage());
            return false;
        }
    }

    // Clase interna temporal o puedes usar DTO real si lo tienes
    static class Beverage {
        private String name;
        private String size;
        private double price;

        // Getters y setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
