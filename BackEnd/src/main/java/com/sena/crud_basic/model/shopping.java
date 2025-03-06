package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="shopping")

public class shopping {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_shopping")
    private int id_shopping;

    @Column(name="id_user",length = 50,nullable = false)
    private int id_user;

    @Column(name="id_enter",length = 150,nullable = false)
    private int id_enter;

    @Column(name="amount",length = 150,nullable = false)
    private int amount;

    @Column(name="date_shopping",length = 150,nullable = false)
    private String date_shopping;

    //constructor
    public shopping (int id_shopping,int id_user,int id_enter, int amount, String date_shopping){
        this.id_shopping = id_shopping;
        this.id_user = id_user;
        this.id_enter = id_enter;
        this.amount = amount;
        this.date_shopping = date_shopping;
     }

     //get del ID  
     public int getId_shopping() {
        return id_shopping;
     }

     //set del ID 
     public void setId_shopping(int id_shopping){
        this.id_shopping=id_shopping;
     }

    //get del firstName  
    public int getId_user() {
      return id_user;
     }
    
     //set del firstName 
    public void set_breedName(int id_user){
         this.id_user=id_user;
     }

      //get del phone  
    public int getId_enter() {
        return id_enter;
      }
      
     //set del phone 
    public void set_contrasena(int id_enter){
       this.id_enter=id_enter;
    }

    public int get_amount() {
      return amount;
    }
    
    //set del phone 
    public void set_fecha_registro(int amount){
       this.amount = amount;
   }
   
   public String get_date_shopping() {
      return date_shopping;
    }
    
    //set del phone 
    public void set_date_shopping(String date_shopping){
       this.date_shopping = date_shopping;
   }
}