package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="comment")

public class comment {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_comment") 
    private int id_comment;

    @Column(name="id_user",length = 50,nullable = false)
    private int id_user;

    @Column(name="id_event",length = 150,nullable = false)
    private int id_event;

    @Column(name="comment",length = 150,nullable = false)
    private String comment;

    @Column(name="date_comment",length = 150,nullable = false)
    private String date_comment;

    //constructor
    public comment (int id_comment,int id_user,int id_event, String comment, String date_comment){
        this.id_comment = id_comment;
        this.id_user = id_user;
        this.id_event = id_event;
        this.comment = comment;
        this.date_comment = date_comment;
     }

     //get del ID  
     public int getId_comment() {
        return id_comment;
     }

     //set del ID 
     public void setId_usuario(int id_comment){
        this.id_comment=id_comment;
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
    public int getId_event() {
        return id_event;
      }
      
     //set del phone 
    public void set_contrasena(int id_event){
       this.id_event=id_event;
    }

    public String get_comment() {
      return comment;
    }
    
    //set del phone 
    public void set_fecha_registro(String comment){
       this.comment = comment;
   }
   
   public String get_date_comment() {
      return date_comment;
    }
    
    //set del phone 
    public void set_date_comment(String date_comment){
       this.date_comment = date_comment;
   }
}