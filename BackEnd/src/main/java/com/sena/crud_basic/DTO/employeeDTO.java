package com.sena.crud_basic.DTO;

public class employeeDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String imageUrl; // Agregar la URL de la imagen

    // Constructor
    public employeeDTO(String firstName, String lastName, String address, String phoneNumber, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl; // Inicializar la propiedad imageUrl
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter y Setter para la URL de la imagen
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
