package com.sena.crud_basic.DTO;

public class PageDTO {
    private String name;

    // Constructor sin parámetros
    public PageDTO() {
    }

    // Constructor con parámetro
    public PageDTO(String name) {
        this.name = name;
    }

    // Getter y setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}