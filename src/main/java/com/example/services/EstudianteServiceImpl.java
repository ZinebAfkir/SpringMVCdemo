package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.EstudianteDao;
import com.example.entities.Estudiante;


@Service  // para que busque los beans 
public class EstudianteServiceImpl implements  EstudianteService{ //Es una clase que implementa la interfaz EstudianteService




    @Autowired  //Mi clase EstudianteServiceImpl para que pueda implementar todos los metodos siguientes de la interfaz necesita 
    // una dependencia con el dao 
    private EstudianteDao estudianteDao;

    @Override
    public List<Estudiante> findAll() {
        // TODO Auto-generated method stub // este tamb lo borro en este y en todos para que se quede mas bonito
       //throw new UnsupportedOperationException("Unimplemented method 'findAll'"); borro esta plantilla de excepcion y pongo mi return en este  metodo y en todos
       return estudianteDao.findAll(); // y aqui no ponemos el get porq el metodo findAll no me devuelve un estudiante solo sino una lista de estudiantes 
    }

    @Override
    public Estudiante findById(int idEstudiante) {
        return estudianteDao.findById(idEstudiante).get(); //ponemos el get porq el metodo findById no devuleve el estudiante sino un opcional que es una caja y para sacar este estudiante de esta caja le ponemos el get 
    }

    @Override
    @Transactional
    public void save(Estudiante estudiante) {
        //return estudianteDao.save(estudiante); como es un metodo void no ponemos el return 
        estudianteDao.save(estudiante);
    }

    @Override
    @Transactional
    public void deleteById(int idEstudiante) {
        estudianteDao.deleteById(idEstudiante);
    }

    @Override
    @Transactional
    public void delete(Estudiante estudiante) {
        estudianteDao.delete(estudiante);
        
    } 
    
}
