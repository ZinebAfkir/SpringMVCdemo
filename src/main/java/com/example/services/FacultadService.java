package com.example.services;

import java.util.List;

import com.example.entities.Facultad;

public interface FacultadService {
    

    public List<Facultad> findAll(); //dame o encuentrame todas las facultades 
     public Facultad findById(int idFacultad); //buscamelo la facultad por su id 
     public void save(Facultad facultad);  
     public void deleteById(int idFacultad);
}
