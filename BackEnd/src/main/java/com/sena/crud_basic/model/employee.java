package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * Agregamos la anotación @Entity
 * para indicar que la clase es una entidad
 * name="nombre que se registra en la base de datos"
 */

@Entity(name="employee")  // Mantengo "employee" en minúsculas
public class employee {  // Mantengo el nombre de la clase en minúsculas como lo prefieres

    /*
     * Atributos o columnas de la entidad
     * @Id = es una llave primaria o PK
     * @Column = columna en la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_employee")
    private int id_employee;

    @Column(name="first_name", length=30, nullable = false)
    private String first_name;

    @Column(name="last_name", length=30, nullable = false)
    private String last_name;

    @Column(name="address", length=60, nullable = false)
    private String address;

    @Column(name="phone_number", length=15, nullable = false)
    private String phone_number;

    @Column(name="status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Nueva columna para la URL de la imagen
    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    // Constructor vacío
    public employee() {}

    // Constructor con parámetros
    public employee(String first_name, String last_name, String address, String phone_number, boolean status, String imageUrl) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    // Getters y Setters

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean getStatus() {
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
