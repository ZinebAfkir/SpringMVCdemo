package com.example.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefonos") //nombre de mi tabla en mysql

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Telefono implements Serializable{

    private static final long serialVersionUID = 1L;
  
    // las tablas tienen que tener id y para que ese id de dichas tablas sea autoincremental y Foreign key hacemos lo siguiente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;
    

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn (name = "idEstudiante")

    private Estudiante estudiante; 
    
}
