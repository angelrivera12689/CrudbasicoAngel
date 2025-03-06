package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="enter")

public class enter {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_enter")
    private int id_enter;

    @Column(name="id_event",length = 50,nullable = false)
    private int id_event;

    @Column(name="price",length = 150,nullable = false)
    private int price;

    @Column(name="available_quantity",length = 150,nullable = false)
    private String available_quantity;

    //constructor
    public enter (int id_enter,int id_event,int price, String available_quantity){
        this.id_enter = id_enter;
        this.id_event = id_event;
        this.price = price;
        this.available_quantity = available_quantity;
     }

     //get del ID  
     public int getId_enter() {
        return id_enter;
     }

     //set del ID 
     public void setId_enter(int id_enter){
        this.id_enter=id_enter;
     }

    //get del firstName  
    public int getid_event() {
      return id_event;
     }
    
     //set del firstName 
    public void set_breedName(int id_event){
         this.id_event=id_event;
     }

      //get del phone  
    public int get_price() {
        return price;
      }
      
     //set del phone 
    public void set_price(int price){
       this.price=price;
    }

    public String get_available_quantity() {
      return available_quantity;
    }
    
    //set del phone 
    public void set_fecha_registro(String available_quantity){
       this.available_quantity = available_quantity;
   }
}