package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;


//En la capa de controller  no hace falta un request y un response esto se resume en lo siguiente

@Controller
@RequestMapping("/")
public class MainController { //El MainController responde a todas las peticiones que estan despues del localhost 8080
    
    @Autowired
    private EstudianteService estudianteService;

    //tengo que inyectar el servicio de facultad, es decir tener acceso a la lista de facultades y como el controller solo tiene 
    //acceso al service por tanto tengo que poner facultadService
    @Autowired
    private FacultadService facultadService;


    //El Main Controller RESPONDE a una peticion concreta y la delega o "manda a hacer" la peticion  posteriormente en un metodo que tiene en cuenta 
    //el verbo (GET,POST, PUT, DELETE,OPTION) del protocolo HTTP utilizado para realizar la peticion

    /*
     * El metodo siguiente devuelve un listado de estudiantes 
     */
    @GetMapping("/listar")
    public ModelAndView listar(){ 
       
        List<Estudiante> estudiantes = estudianteService.findAll();// el metodo findAll devuelve una lista de estuiantes 
        
        ModelAndView mav = new ModelAndView("views/listaEstudiantes");//creo un objeto de tipo ModelAndView
        //siendo listaEstudiantes el nombre de la clase .html
        
        mav.addObject("estudiantes",estudiantes);
        return mav; 
    }

    /**
     * Muestra el formulario de alta de estudiante
     */

     @GetMapping("/frmAltaEst") // aqui es el nombre de la url que va a resoponder y le damos el nombre que quieras no tiene porq ser igual que el nombre de abajo 
     public String formularioAltaEstudiante(Model model){

// Lo siguiente es para recorrer mi lista de facultades, es decir para tener un despliegue con el nombre de todas las facul
//a la hora de rellenar el formulario en mi pagina html, eso lo hacemos para no estar metiendo el nombre de mis facultades 
// de una en una como option value en formularioEstudinate.html

     List<Facultad> facultades = facultadService.findAll();

        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";
     }
}
