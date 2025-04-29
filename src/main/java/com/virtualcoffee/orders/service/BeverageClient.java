package com.virtualcoffee.orders.service;

import org.springframework.stereotype.Component;

@Component
public class BeverageClient {

    public boolean isAvailable(String name) {
        // Este método será mockeado en pruebas
        return true;
    }
}
