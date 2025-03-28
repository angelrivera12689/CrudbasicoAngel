package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "CategoryEvent") // Table name in the database
public class CategoryEvent {

    // Attributes or columns of the entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")  // Column name in the database
    private int category_id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;
    
    public CategoryEvent() {
        // Constructor vacío requerido por JPA
    }
    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;
    // Constructor
    public CategoryEvent(int category_id, String name, String description, boolean status) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getcategory_id() {
        return category_id;
    }

    public void setcategory_id(int category_id) {
        this.category_id= category_id;
    }

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
    public boolean getStatus() {
        return status;
     }
    
     public void setStatus(boolean status) {
        this.status = status;
     }
}
