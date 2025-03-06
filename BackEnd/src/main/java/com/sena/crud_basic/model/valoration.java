package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="valoration")

public class valoration {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_valoration")
    private int id_valoration;

    @Column(name="id_user",length = 50,nullable = false)
    private int id_user;

    @Column(name="id_event",length = 150,nullable = false)
    private int id_event;

    @Column(name="puntuation",length = 150,nullable = false)
    private int puntuation;

    @Column(name="date_valoration ",length = 150,nullable = false)
    private String date_valoration ;

    //constructor
    public valoration (int id_valoration,int id_user,int id_event, int puntuation, String date_valoration ){
        this.id_valoration = id_valoration;
        this.id_user = id_user;
        this.id_event = id_event;
        this.puntuation = puntuation;
        this.date_valoration  = date_valoration ;
     }

     //get del ID  
     public int getId_valoration() {
        return id_valoration;
     }

     //set del ID 
     public void setId_valoration(int id_valoration){
        this.id_valoration=id_valoration;
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
    public int getid_event() {
        return id_event;
      }
      
     //set del phone 
    public void set_contrasena(int id_event){
       this.id_event=id_event;
    }

    public int get_puntuation() {
      return puntuation;
    }
    
    //set del phone 
    public void set_fecha_registro(int puntuation){
       this.puntuation = puntuation;
   }
   
   public String get_date_valoration () {
      return date_valoration ;
    }
    
    //set del phone 
    public void set_date_valoration (String date_valoration ){
       this.date_valoration  = date_valoration ;
   }
}