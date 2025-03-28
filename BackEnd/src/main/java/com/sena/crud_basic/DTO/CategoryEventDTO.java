package com.sena.crud_basic.DTO;

public class CategoryEventDTO {

    
    private String name;
    private String description;

    // Constructor
    public CategoryEventDTO(int category_id, String name, String description) {
       
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
