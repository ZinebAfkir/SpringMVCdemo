package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;


@Repository

public interface TelefonoDao extends JpaRepository <Telefono,Integer>{ //Interfaz de la clase Estudiante
    
    //Consulta tal cual está en mysql y cambiamos el ? por el id de estudiante 
    //@Query(value = "delete from telefonos where estudiante_id = :idEstudiante", nativeQuery = true)
    
    //metodo que me bora un estudiante por su id
    //long deleteByIdEstudiante(@Param("idEstudiante")Integer IdEstudiante);//como en la consulta hemos sustituido ? por el id 
    //aqui tenemos que añadir (@Param("idEstudiante")) 

    long deleteByEstudiante(Estudiante estudiante);

    //List<Telefono> findByEstudiante(Estudiante estudiante);

    List<Telefono> findByEstudiante(Estudiante estudiante);

}


