package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Assistant")
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assistant")
    private int id_assistant;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Nuevo campo para la imagen
    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    public Assistant() {}

    public Assistant(int id_assistant, String name, String email, String phone, boolean status, String imageUrl) {
        this.id_assistant = id_assistant;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.imageUrl = imageUrl;
    }

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

    // Getter y Setter para el campo de la URL de la imagen
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // OPCIONAL: si alguna parte del código lo necesita, podés dejarlo
    public int getAssistantId() {
        return this.id_assistant;
    }
}
