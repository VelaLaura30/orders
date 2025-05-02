package com.virtualcoffee.orders.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "Name and Size cannot be empty")
    private String name;

    @NotBlank(message = "Name and Size cannot be empty")
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "OrderRequest [name=" + name + ", size=" + size + "]";
    }

}