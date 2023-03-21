package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.services.EstudianteService;


//En la capa de controller  no hace falta un request y un response esto se resume en lo siguiente

@Controller
@RequestMapping("/") // va a mandar todo lo que este delante de la barra en la url
public class MainController { //El MainController responde a todas las peticiones que estan despues del localhost 8080
    
    @Autowired
    private EstudianteService estudianteService;

    //El Main Controller RESPONDE a una peticion concreta y la delega o "manda a hacer" la peticion  posteriormente en un metodo que tiene en cuenta 
    //el verbo (GET,POST, PUT, DELETE,OPTION) del protocolo HTTP utilizado para realizar la peticion

    /*
     * El metodo siguiente devuelve un listado de estudiantes 
     */
    @GetMapping("/listar")
    public ModelAndView listar(){ 
       
        List<Estudiante> estudiantes = estudianteService.findAll();// el metodo findAll devuelve una lista de estuiantes 
        
        ModelAndView mav = new ModelAndView("views/listaEstudiantes");//creo un objeto de tipo ModelAndView
        //siendo listaEstudiantes el nombre de la clase .html dnd el nombre de la view o vista es listaE...
        
        mav.addObject("estudiantes",estudiantes);
        return mav; 
    }

    /**
     * Muestra el formulario de alta de estudiante
     */

     @GetMapping("/frmAltaEst") // aqui es el nombre de la url que va a resoponder y le damos el nombre que quieras no tiene porq ser igual que el nombre de abajo 
     public String formularioAltaEstudiante(Model model){

        model.addAttribute("estudiante", new Estudiante());
        return "views/formularioAltaEstudiante";

     }




        /**
         * Metodo que recibe los datos procedentes de los controladores del formulario 
         */

         @PostMapping("/altaEstudiante")
        public void altaEstudiante(){

        }
     
}
