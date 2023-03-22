package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;


//En la capa de controller  no hace falta un request y un response esto se resume en lo siguiente

@Controller
@RequestMapping("/") // va a mandar todo lo que este delante de la barra en la url
public class MainController { //El MainController responde a todas las peticiones que estan despues del localhost 8080
    
    // Logger resgistra todo lo que pasa en esta clase, MainController para saber todo lo que pasa por si algo va mal como
    //por ejemplo si se borra la consola o algo
    public static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private EstudianteService estudianteService;

    //tengo que inyectar el servicio de facultad, es decir tener acceso a la lista de facultades y como el controller solo tiene 
    //acceso al service por tanto tengo que poner facultadService
    @Autowired
    private FacultadService facultadService;

    @Autowired
    private TelefonoService telefonoService;


    //El Main Controller RESPONDE a una peticion concreta y la delega o "manda a hacer" la peticion  posteriormente en un metodo que tiene en cuenta 
    //el verbo (GET,POST, PUT, DELETE,OPTION) del protocolo HTTP utilizado para realizar la peticion

    /*
     * El metodo siguiente devuelve un listado de estudiantes 
     */
    @GetMapping("/listar") // todo lo que se envie por get se ve en la url, es decir esta en la cabecera y lo puede ver todo el mundo
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

// Lo siguiente es para recorrer mi lista de facultades, es decir para tener un despliegue con el nombre de todas las facul
//a la hora de rellenar el formulario en mi pagina html, eso lo hacemos para no estar metiendo el nombre de mis facultades 
// de una en una como option value en formularioEstudinate.html

     List<Facultad> facultades = facultadService.findAll();

        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";

     }




        /**
         * Metodo que recibe los datos procedentes de los controladores del formulario 
         *
         */

         @PostMapping("/altaEstudiante") //post es para que lo que envie este dentro del protocolo es decir no lo puede ver todo el mundo
        public String altaEstudiante(@ModelAttribute Estudiante estudiante,
                      @RequestParam(name ="numerosTelefonos") String telefonosRecibidos){

//"numerosTelefonos" es una request es decir es una peticion que hacemos,pedimos "numerosTelefonos" y lo que me da lo guardamos
// en la variable String telefonosRecibidos
 

// gracias al log nos da un mensaje de comprobación antes de procesar la información. Es una buena práctica de programación 
//hacer esta comprobación previa

            LOG.info("Telefonos recibidos: " + telefonosRecibidos);

            List<String> listadoNumerosTelefonos = null;

            if(telefonosRecibidos != null) {//Convertimos a string cuando hemos comprobado que no es null

    // separa el array cada vez que encuentra un ; podría pedirle que separase cada vez que encuentre un espacio o lo que tu le metas dentro del () del split
            String[] arrayTelefonos = telefonosRecibidos.split(";"); 
            //Yo introduzco la lista de telefonos a mano y los 
            //separo en ; entonces con el split cada vez que haya el ; lo reconoce y separa los telefonos por el ;
            //yo entre() del split he puesto ; pero podria poner cualquier cosa y el me separara los teelfonos segun lo que 
            // le he metido

            // Convertimos este array en una colección para luego pasarlo a flujo y trabajar con ese flujo:
            listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);
            }

            //Lo siguiente te lo guarda
            //Primero se guarda el estudiante para poder acceder a él y poder meterles los telefonos
             estudianteService.save(estudiante); //guarada el estudiante en la bbdd estudiante

             //Si sí hay telefonos, convertimos la coleccion en un flujo de telefonos, recorremos el flujo e introducimos los telefonos
             // n es el numero de telefono que pasa por la tuberia, le podemos llamar n o lo que queramos y quieres que esta n
             // se guarde en la variable numero por eso ponemos numero(n)
             if(listadoNumerosTelefonos!= null) {
                listadoNumerosTelefonos.stream().forEach(n->{
                     Telefono telefonoObject = Telefono.builder().numero(n).estudiante(estudiante).build();

                     telefonoService.save(telefonoObject);
                });
                
             }
             //Lo siguiente te lo muestra
            return "redirect:/listar"; // es a la url a cual nos llevara despues de haber rellenado el formulario del estudiante 
           
        }
     
}
