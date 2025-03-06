package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="categoria")

public class categoria {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria\t") 
    private int id_categoria;

    @Column(name="nombre_categoria",length = 50,nullable = false)
    private String nombre_categoria;

    //constructor
    public categoria (int id_categoria,String nombre_categoria ){
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
     }

     //get del ID  
     public int getid_categoria() {
        return id_categoria;
     }

     //set del ID 
     public void setid_categoria(int id_categoria){
        this.id_categoria=id_categoria;
     }

    //get del firstName  
    public String get_nombre_categoria() {
      return nombre_categoria;
     }
    
     //set del firstName 
    public void set_nombre_categoria(String nombre_categoria){
         this.nombre_categoria=nombre_categoria;
     }
}