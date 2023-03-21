package com.example.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "facultades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Facultad implements Serializable{ //todas las clases que se van a usar para generar una tabla tiene que llevar el implements Serializable 
    
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private int id;
    private String nombre;
   
    @OneToMany (fetch = FetchType.EAGER, 
    cascade =CascadeType.PERSIST, mappedBy = "facultad" ) // tenemos que añadir el mappedBy con el nombre de la tabla padre para decir quien posee la relacion de clave externa, es decir la que posee la de"muchos" es decir la tabla padre dnd se crea el FK
//facultad es el nombre del campo creado en la clase Estudiante, mientras que en vrdd el nombre de la tabla es facultades 
    private List<Estudiante> estudiantes;

}
