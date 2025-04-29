package com.virtualcoffee.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcoffee.orders.controller.OrderController;
import com.virtualcoffee.orders.dto.OrderRequest;
import com.virtualcoffee.orders.model.Order;
import com.virtualcoffee.orders.service.BeverageClient;
import com.virtualcoffee.orders.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private BeverageClient beverageClient;

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void testCreateOrderWhenDrinkIsAvailable() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName("Coke");
        orderRequest.setSize("Large");

        Mockito.when(beverageClient.isAvailable("Coke")).thenReturn(true);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Order created successfully"));

        Mockito.verify(orderService, Mockito.times(1)).createOrder(orderRequest);
    }

    @Test
    void testCreateOrderWhenDrinkIsNotAvailable() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName("Pepsi");
        orderRequest.setSize("Medium");

        Mockito.when(beverageClient.isAvailable("Pepsi")).thenReturn(false);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Beverage not available"));

        Mockito.verify(orderService, Mockito.never()).createOrder(orderRequest);
    }

    @Test
    void testGetAllOrders() throws Exception {
        Order order1 = new Order("Coke", "Large");
        Order order2 = new Order("Pepsi", "Medium");

        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(order1, order2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Coke"))
                .andExpect(jsonPath("$[1].name").value("Pepsi"));
    }

    @Test
    void testCreateOrderWhenNameIsEmpty() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName("");  // Nombre vacío
        orderRequest.setSize("Large");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Name and Size cannot be empty"));
    }

    @Test
    void testCreateOrderWhenSizeIsEmpty() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName("Coke");
        orderRequest.setSize("");  // Tamaño vacío

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                        .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name and Size cannot be empty"));
    }


    /* @Test
    void testCreateOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setName("Latte");
        request.setSize("Grande");

        Order order = new Order("Latte", "Grande");

        when(orderService.createOrder(request)).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Latte"));
    }

    @Test
    void testGetOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(new Order("Mocha", "Pequeño")));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mocha"));
    } */
}