package com.virtualcoffee.orders.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderSteps {

    private String bebida;
    private ResponseEntity<String> response;

    @Given("que la bebida {string} está registrada en el menú")
    public void bebida_registrada(String nombre) {
        this.bebida = nombre;
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/beverages/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"name\": \"%s\", \"size\": \"Pequeño\", \"price\": 2.50}", nombre);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(url, request, String.class);
    }

    @Given("que la bebida {string} no está en el menú")
    public void bebida_no_registrada(String nombre) {
        this.bebida = nombre;
        // No se hace nada, simplemente no se registra
    }

    @When("el cliente pide la bebida {string} desde el frontend de React")
    public void cliente_pide_bebida(String nombre) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/orders";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"customerName\": \"Tester\", \"beverages\": [\"%s\"]}", nombre);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        response = restTemplate.postForEntity(url, request, String.class);
    }

    @Then("el sistema debe registrar el pedido con la bebida {string}")
    public void verificar_pedido(String nombreEsperado) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(nombreEsperado));
    }

    @Then("el sistema debe mostrar un mensaje indicando que la bebida no está disponible")
    public void bebida_no_disponible() {
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toLowerCase().contains("no disponible") || response.getBody().toLowerCase().contains("error"));
    }
}