package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="roles")

public class roles {
    /*
     * atributos o columnas de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_roles")
    private int id_roles;

    @Column(name="name_rol",length = 50,nullable = false)
    private String name_rol;


    public roles (int id_roles, String name_rol){
        this.id_roles = id_roles;
        this.name_rol = name_rol;
     }

     public int getid_roles() {
        return id_roles;
     }

     public void setid_roles(int id_roles){
        this.id_roles=id_roles;
     }

    public String get_name_rol() {
      return name_rol;
     }
    
    public void set_name_rol(String name_rol){
         this.name_rol=name_rol;
     }
}