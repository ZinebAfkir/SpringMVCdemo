package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entities.Estudiante;


@Repository // con eso le decirmos a spring que busque los beans para todos los metodos que voy a utilizar de la interfaz JpaRepository

public interface EstudianteDao extends JpaRepository <Estudiante,Integer> { //Interfaz de la clase Estudiante


    
}
