package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="payments")

public class payments {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_payments")
    private int id_payments;

    @Column(name="id_shopping",length = 50,nullable = false)
    private int id_shopping;

    @Column(name="payment_method",length = 150,nullable = false)
    private String payment_method;

    @Column(name="state",length = 150,nullable = false)
    private String state;

    @Column(name="date_payment",length = 150,nullable = false)
    private String date_payment;


    public payments (int id_payments,int id_shopping ,String payment_method, String state, String date_payment){
        this.id_payments = id_payments;
        this.id_shopping = id_shopping;
        this.payment_method = payment_method;
        this.state = state;
        this.date_payment = date_payment;
     }

     public int getid_payments() {
        return id_payments;
     }

     public void setid_payments(int id_payments){
        this.id_payments=id_payments;
     }

    public int get_id_shopping() {
      return id_shopping;
     }
    
    public void set_id_shopping(int id_shopping){
         this.id_shopping=id_shopping;
     }

    public String get_payment_method() {
        return payment_method;
      }
      
     public void set_payment_method(String payment_method){
       this.payment_method=payment_method;
    }

    public String get_state() {
      return state;
    }
    
    public void set_state(String state){
       this.state = state;
   }

   public String get_date_payment() {
      return date_payment;
    }
    
    public void set_date_payment(String date_payment){
      this.date_payment = date_payment;
    }
}