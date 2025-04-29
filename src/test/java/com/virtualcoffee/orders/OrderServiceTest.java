package com.virtualcoffee.orders;

import com.virtualcoffee.orders.dto.OrderRequest;
import com.virtualcoffee.orders.model.Order;
import com.virtualcoffee.orders.service.BeverageClient;
import com.virtualcoffee.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class OrderServiceTest {

    private OrderService orderService;
    private BeverageClient beverageClient;

    @BeforeEach
    void setUp() {
        beverageClient = mock(BeverageClient.class);
        orderService = new OrderService(beverageClient);
    }

    @Test
    void shouldCreateOrderIfBeverageAvailable() {
        OrderRequest request = new OrderRequest();
        request.setName("Latte");
        request.setSize("Grande");

        when(beverageClient.isAvailable("Latte")).thenReturn(true);

        Order order = orderService.createOrder(request);

        assertEquals("Latte", order.getName());
        assertEquals("Grande", order.getSize());
    }

    @Test
    void shouldThrowExceptionIfBeverageUnavailable() {
        OrderRequest request = new OrderRequest();
        request.setName("Unknown");
        request.setSize("Pequeño");

        when(beverageClient.isAvailable("Unknown")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(request);
        });

        assertEquals("La bebida no está disponible", exception.getMessage());
    }

    @Test
    void shouldReturnAllOrders() {
        when(beverageClient.isAvailable("Latte")).thenReturn(true);

        OrderRequest req1 = new OrderRequest();
        req1.setName("Latte");
        req1.setSize("Grande");

        orderService.createOrder(req1);

        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
    }
}