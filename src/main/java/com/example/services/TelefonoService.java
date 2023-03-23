package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;

public interface TelefonoService {
    

    public List<Telefono> findAll(); 
     public Telefono findById(int idTelefono); //en la clase Telefono se llama id pero aqui entre() le doy el nombre que yo quiera
     public void save(Telefono telefono); 
     public void deleteById(int idTelefono);
     public void deleteByEstudiante(Estudiante estudiante);

     public List<Telefono> findByEstudiante(Estudiante estudiante);
}


