package com.sena.crud_basic.DTO;

public class organizerDTO {

    private String name;
    private String phone;
    private String email;
    private String imageUrl;
    // Constructor
    public organizerDTO(String name, String phone, String email, String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
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
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}
