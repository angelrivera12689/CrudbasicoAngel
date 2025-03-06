package com.sena.crud_basic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "user")

public class user {
   /*
    * atributos o columnas de la entidad
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_usuario")
   private int id_usuario;

   @Column(name = "nombre", length = 50, nullable = false)
   private String nombre;

   @Column(name = "email", length = 150, nullable = false)
   private String email;

   @Column(name = "contrasena", length = 150, nullable = false)
   private String contrasena;

   @Column(name = "telefono", length = 150, nullable = false)
   private int telefono;

   @Column(name = "fecha_registro", nullable = false)
   private LocalDateTime fecha_registro;

   // constructor
   public user(int id_usuario, String nombre, String email, String contrasena, int telefono,
         LocalDateTime fecha_registro) {
      this.id_usuario = id_usuario;
      this.nombre = nombre;
      this.email = email;
      this.contrasena = contrasena;
      this.telefono = telefono;
      this.fecha_registro = fecha_registro;
   }

   // get del ID
   public int getId_usuario() {
      return id_usuario;
   }

   // set del ID
   public void setId_usuario(int id_usuario) {
      this.id_usuario = id_usuario;
   }

   // get del firstName
   public String get_nombre() {
      return nombre;
   }

   // set del firstName
   public void set_breedName(String nombre) {
      this.nombre = nombre;
   }

   // get del phone
   public String get_contrasena() {
      return contrasena;
   }

   // set del phone
   public void set_contrasena(String contrasena) {
      this.contrasena = contrasena;
   }

   public int get_telefono() {
      return telefono;
   }

   // set del phone
   public void set_telefono(int telefono) {
      this.telefono = telefono;
   }

   public LocalDateTime get_fecha_registro() {
      return fecha_registro;
   }

   // set del phone
   public void set_fecha_registro(LocalDateTime fecha_registro) {
      this.fecha_registro = fecha_registro;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

}