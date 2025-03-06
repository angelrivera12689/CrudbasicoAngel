package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Organizer") // Table name in the database
public class organizer {

    // Attributes or columns of the entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizer")  // Column name in the database
    private int id_organizer;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    // Constructor
    public organizer(int id_organizer, String name, String phone, String email) {
        this.id_organizer = id_organizer;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters

    public int getId_organizer() {
        return id_organizer;
    }

    public void setId_organizer(int id_organizer) {
        this.id_organizer = id_organizer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
