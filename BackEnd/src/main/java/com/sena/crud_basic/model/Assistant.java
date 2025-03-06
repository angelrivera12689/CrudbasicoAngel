package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Assistant") // Table name in the database
public class Assistant {

    // Attributes or columns of the entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assistant")  // Column name in the database
    private int id_assistant;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    // Constructor
    public Assistant(int id_assistant, String name, String email, String phone) {
        this.id_assistant = id_assistant;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters

    public int getId_assistant() {
        return id_assistant;
    }

    public void setId_assistant(int id_assistant) {
        this.id_assistant = id_assistant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void save(Assistant assistant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
