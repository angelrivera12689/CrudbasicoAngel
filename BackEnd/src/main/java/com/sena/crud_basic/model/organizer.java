package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "organizer") // Table name in the database
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

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    public organizer() {
    }

    public organizer(int id_organizer, String name, String phone, String email, boolean status, String imageUrl) {
        this.id_organizer = id_organizer;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.imageUrl = imageUrl;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
}


