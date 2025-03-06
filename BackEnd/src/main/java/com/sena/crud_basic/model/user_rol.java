package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "user_rol")

public class user_rol {
   /*
    * atributos o columnas de la entidad
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_user_rol")
   private int id_user_rol;
   
   @ManyToOne
   @JoinColumn(name = "id_usuario")
   private user user;

   @Column(name = "id_rol", length = 50, nullable = false)
   private int id_rol;

   public user_rol(com.sena.crud_basic.model.user user, int id_rol) {
      this.user = user;
      this.id_rol = id_rol;
   }

   public user getUser() {
      return user;
   }

   public void setUser(user user) {
      this.user = user;
   }

   public int get_name_rol() {
      return id_rol;
   }

   public void set_name_rol(int id_rol) {
      this.id_rol = id_rol;
   }
}