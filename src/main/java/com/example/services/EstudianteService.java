package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;

public interface EstudianteService {
    
    //Debe devolver una lista de estudiantes
   
     public List<Estudiante> findAll(); //trae todos los estudiantes 
     public Estudiante findById(int idEstudiante); //buscamelo por id 
     public void save(Estudiante estudiante); //guardame todos los estudiante que hayas encontrado y aqui no hace falta poner el id 
     public void deleteById(int idEstudiante);
     public void delete(Estudiante estudiante);

     //Lo nuevo para las fotos

     
     /**
      * El metodo update no es necesario porq el "save" inserta o actualiza en dependencia de que el idEstudiante exista o no, es 
        decir si no existe lo crea y si existe actualiza la informacion 
      */
}
