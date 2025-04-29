package com.virtualcoffee.orders.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message="Nombre de bebida es obligatorio, no puede estar vacío")
    private String name;

    @NotBlank(message="Tamaño de bebida es obligatorio, no puede estar vacío")
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