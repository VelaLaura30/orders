package com.virtualcoffee.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualcoffee.orders.dto.OrderRequest;
import com.virtualcoffee.orders.model.Order;
import com.virtualcoffee.orders.service.BeverageClient;
import com.virtualcoffee.orders.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    private BeverageClient beverageService;

    @Autowired
    public OrderController(OrderService orderService, BeverageClient beverageService) {
        this.orderService = orderService;
        this.beverageService = beverageService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        if (orderRequest.getName().isEmpty() || orderRequest.getSize().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre o tamaño no pueden estar vacíos");
        }
        boolean isAvailable = beverageService.isAvailable(orderRequest.getName());

        if (!isAvailable) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Beverage not available");
        }

        orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}