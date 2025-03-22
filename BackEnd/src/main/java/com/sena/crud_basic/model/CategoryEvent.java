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
    @Column(name = "id_category")  // Column name in the database
    private int id_category;

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
    public CategoryEvent(int id_category, String name, String description, boolean status) {
        this.id_category = id_category;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
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
