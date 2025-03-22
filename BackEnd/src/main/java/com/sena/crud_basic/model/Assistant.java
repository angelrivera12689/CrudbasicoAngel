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

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    public Assistant() {
        // Este constructor es necesario para JPA/Hibernate
    }
    // Constructor
    public Assistant(int id_assistant, String name, String email, String phone, boolean status) {
        this.id_assistant = id_assistant;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    // Getters and Setters
// Getter para 'id'
public int getId() {
    return id_assistant;
}

public void setId(int id) {
    this.id_assistant = id;
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

public boolean getStatus() {
    return status;
 }

 public void setStatus(boolean status) {
    this.status = status;
 }

 
}
