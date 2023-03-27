package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiantes") //nombre de mi tabla en mysql
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Estudiante implements Serializable { //Serializable convierte un objeto en un flujo
    private static final long serialVersionUID = 1L;
  
    // las tablas tienen que tener id y para que ese id de dichas tablas sea autoincremental y Foreign key hacemos lo siguiente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotNull(message = "El nombre no puede ser null")
    //@Size(max=25 , min=4)

    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAlta;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    
    private Genero genero;
    private double beca;
   
    private String foto; //como tenemos el lombok ya se habra creado los metodos get y set foto
    
    //Es mejor usar lazy para tener solo conexion cuando haya consulta entre tablas y no una conexion activa permanente entre ellas
    // tal como hacia eager
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)//muchos a uno, es decir muchos estudiantes pertenecen a una facultad
    @JoinColumn (name = "idFacultad")
    private Facultad facultad; 

    //Tiene que ser remove porq antes de borrar el padre tengo que borrar los hijos 
    //cuando borre el estudiante ya me elimine los telefonos 
    
     @OneToMany (fetch = FetchType.LAZY, cascade =CascadeType.REMOVE, mappedBy = "estudiante" ) //uno a muchos, es decir un estudiante puede tener muchos telefonos 
    private List<Telefono> telefonos; // el meppedBy hace referencia a la parte de "muchos"


    public enum Genero{
        HOMBRE,MUJER,OTRO
    }
}
