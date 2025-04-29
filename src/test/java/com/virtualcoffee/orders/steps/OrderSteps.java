package com.virtualcoffee.orders.steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OrderSteps {

    private Response response;

    @Given("the beverage {string} exists in the menu")
    public void the_beverage_exists_in_the_menu(String beverageName) {
        response = RestAssured.given()
                .contentType("application/json")
                .body("{\"name\":\"" + beverageName + "\", \"size\":\"Medium\"}")
                .post("http://localhost:5000/beverages");
        assertEquals(201, response.statusCode());
    }

    @Given("the beverage {string} does not exist in the menu")
    public void the_beverage_does_not_exist_in_the_menu(String beverageName) {
        // No hacemos nada
    }

    @When("I order the beverage {string} with size {string}")
    public void i_order_the_beverage(String beverageName, String size) {
        response = RestAssured.given()
                .contentType("application/json")
                .body("{\"name\":\"" + beverageName + "\", \"size\":\"" + size + "\"}")
                .post("http://localhost:8080/orders");
    }

    @Then("the order should be accepted with the message {string}")
    public void the_order_should_be_accepted(String expectedMessage) {
        assertEquals(200, response.statusCode());
        assertTrue(response.getBody().asString().toLowerCase().contains(expectedMessage.toLowerCase()));
    }

    @Then("the order should be rejected with the message {string}")
    public void the_order_should_be_rejected(String expectedMessage) {
        assertTrue(response.statusCode() == 400 || response.statusCode() == 404);
        assertTrue(response.getBody().asString().toLowerCase().contains(expectedMessage.toLowerCase()));
    }
}

