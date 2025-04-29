package com.virtualcoffee.orders.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualcoffee.orders.dto.OrderRequest;
import com.virtualcoffee.orders.model.Order;

@Service
public class OrderService {

    @Autowired
    private BeverageClient beverageClient;

    private final List<Order> orders = new ArrayList<>();


    @Autowired
    public OrderService(BeverageClient beverageClient) {
        this.beverageClient = beverageClient;
    }

    public Order createOrder(OrderRequest request) {
        if (!beverageClient.isAvailable(request.getName())) {
            throw new IllegalArgumentException("La bebida no está disponible");
        }
        Order order = new Order(request.getName(), request.getSize());
        orders.add(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
