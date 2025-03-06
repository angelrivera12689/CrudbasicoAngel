package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "event")

public class event {
   /*
    * atributos o columnas de la entidad
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_event")
   private int id_event;

   @Column(name = "title", length = 50, nullable = false)
   private int title;

   @Column(name = "description", length = 150, nullable = false)
   private int description;

   @Column(name = "date_event", length = 150, nullable = false)
   private String date_event;

   @Column(name = "ubication", length = 150, nullable = false)
   private String ubication;

   @Column(name = "id_organizer", length = 150, nullable = false)
   private int id_organizer;

   @Column(name = "id_category", length = 150, nullable = false)
   private int id_category;

   public event(int id_event, int title, int description, String date_event, String ubication, int id_organizer,
         int id_category) {
      this.id_event = id_event;
      this.title = title;
      this.description = description;
      this.date_event = date_event;
      this.ubication = ubication;
      this.id_organizer = id_organizer;
      this.id_category = id_category;
   }

   public int getId_event() {
      return id_event;
   }

   public void setId_event(int id_event) {
      this.id_event = id_event;
   }

   public int get_title() {
      return title;
   }

   public void set_title(int title) {
      this.title = title;
   }

   public int get_description() {
      return description;
   }

   public void set_description(int description) {
      this.description = description;
   }

   public String get_date_event() {
      return date_event;
   }

   public void set_fecha_registro(String date_event) {
      this.date_event = date_event;
   }

   public String get_ubication() {
      return ubication;
   }

   public void set_ubication(String ubication) {

      this.ubication = ubication;
   }

   public int getId_organizer() {
      return id_organizer;
   }

   public void setId_organizer(int id_organizer) {
      this.id_organizer = id_organizer;
   }

   public int getId_category() {
      return id_category;
   }

   public void setId_category(int id_category) {
      this.id_category = id_category;
   }
}