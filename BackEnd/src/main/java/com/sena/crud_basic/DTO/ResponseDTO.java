package com.sena.crud_basic.DTO;

public class ResponseDTO {
    private String status;
    private String message;


    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
