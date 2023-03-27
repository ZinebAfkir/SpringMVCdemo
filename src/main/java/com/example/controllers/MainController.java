package com.example.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
     Estudiante estudiante = new Estudiante();

        model.addAttribute("estudiante",estudiante);
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";

     }




        /**
         * Metodo que recibe los datos procedentes de los controladores del formulario 
         *
         */

         @PostMapping("/altaModificacionEstudiante") //post es para que lo que envie este dentro del protocolo es decir no lo puede ver todo el mundo
        public String altaEstudiante(@ModelAttribute Estudiante estudiante,
                      @RequestParam(name ="numerosTelefonos") String telefonosRecibidos,
                      @RequestParam(name="imagen") MultipartFile imagen){ 

//"numerosTelefonos" es una request es decir es una peticion que hacemos,pedimos "numerosTelefonos" y lo que me da lo guardamos
// en la variable String telefonosRecibidos
 

// gracias al log nos da un mensaje de comprobación antes de procesar la información. Es una buena práctica de programación 
//hacer esta comprobación previa

            LOG.info("Telefonos recibidos: " + telefonosRecibidos);
 
            //Si la imagen no viene vacia ya puedo trabajar entonces con ella 
              if(!imagen.isEmpty()){
               try {

               //Ruta relativa dnd voy a almacenar el archivo de imagen
               Path rutaRelativa = Paths.get("src/main/resources/static/images");// el path es un interfaz
               
               //Ruta absoluta es la concatenacion de la ruta relativa con el get... 
               String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();

               //Almacenamos la imagen en un array de bytes 
               byte[] imagenEnBytes = imagen.getBytes();

               //Ruta completa 
               Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + imagen.getOriginalFilename());

               //Ahora tenemos que guardar la imagen en lel file system/rutaAbsoluta

               Files.write(rutaCompleta, imagenEnBytes);

               //Asociar la imagen con el objeto estudiante que se va a guardar 
               estudiante.setFoto(imagen.getOriginalFilename());

               } catch (Exception e) {
                  // TODO: handle exception
               }
              }





             //Lo siguiente te lo guarda
            //Primero se guarda el estudiante para poder acceder a él y poder meterles los telefonos
            estudianteService.save(estudiante); //guarada el estudiante en la bbdd estudiante
             
            //if(estudiante.getId()!=0){
//Si el id del estudiante es distinto de 0 es porq ya existe es decir si es !=0 es porq ahora voy a actualizar
//porq yo en el formulario cuando creo o relleno los datos de un estudiante luego a este se le asigna un id, este estudiante 
//antes no tenia id hasta que yo haya rellenado sus datos
// entonces 
            //     estudianteService.deleteById(estudiante.getId());
            // }

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

            

             //Si sí hay telefonos, convertimos la coleccion en un flujo de telefonos, recorremos el flujo e introducimos los telefonos
             // n es el numero de telefono que pasa por la tuberia, le podemos llamar n o lo que queramos y quieres que esta n
             // se guarde en la variable numero por eso ponemos numero(n)
             if(listadoNumerosTelefonos!= null) {
                telefonoService.deleteByEstudiante(estudiante);
                listadoNumerosTelefonos.stream().forEach(n->{
                     Telefono telefonoObject = Telefono.builder().numero(n).estudiante(estudiante).build();

                     telefonoService.save(telefonoObject);
                });
                
             }
             //Lo siguiente te lo muestra
            return "redirect:/listar"; // es a la url a cual nos llevara despues de haber rellenado el formulario del estudiante 
           //lo anterior me redirecta a la url o pagina listar 
        }
     


/*
 * Metodo para actualizar los datos de un estudiante dado su id
 */

 //como se hace a través de un link, es un get y es visible para todos, solo es post si se lo especificamos nosotros a la hora 
 //de hacer un formulario

       @GetMapping("/fmrActualizar/{id}")
       public String frmactualizaEstudiante(@PathVariable(name ="id") int idEstudiante, // este metodo le paso el id de un estudiante
                                         Model model){ 

        Estudiante estudiante= estudianteService.findById(idEstudiante);
        
        List<Telefono> todosTelefonos = telefonoService.findAll(); // findAll devuelve una lista de todos telefonos 
        
        // ahora todos estos telefonos los paso por un flujo y pasan por una tuberia de uno en uno y solo me quedo  
        //con el telefono cuyo el id de estudiante coincide con el id que le pasamos al metodo actualizaEstudiante,
        // para ello a cada telefono que pasa por la tuberia le pido el id de estudiante asociado a este telefono 

        List<Telefono> telefonosDelEstudiante = todosTelefonos.stream().filter(t->t.getEstudiante().getId() == idEstudiante)
        .collect(Collectors.toList()); 
        
        // pasa por la tuberia los telefonos, el estudiante  y el id del estudiante pero  solo me interesa el numero de telefono 

       // el ; es para que me ajunte los telefonos del estudiante con un ; por si tiene mas de un telefono
        String numerosDeTelefono = telefonosDelEstudiante.stream().map(t->t.getNumero()).collect(Collectors.joining(";"));
        
        List<Facultad> facultades =facultadService.findAll();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("telefonos", numerosDeTelefono);
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante";

       }

       @GetMapping("/borrar/{id}")
       public String borrarEstudiante(@PathVariable(name="id") int idEstudiante){

         estudianteService.delete(estudianteService.findById(idEstudiante));
         
         return "redirect:/listar";

       }

       /**
     * Métodoque encuentre los telefonso de cada estudiante: (hecho por nosotras):
     */
    @GetMapping("/detalles/{id}")
    public String detallesEstudiante(@PathVariable(name = "id") int id, Model model) {

        Estudiante estudiante = estudianteService.findById(id);
        List<Telefono> telefonos = telefonoService.findByEstudiante(estudiante);
        List<String> numerosTelefono = telefonos.stream().map(t -> t.getNumero()).toList();

        model.addAttribute("telefonos", numerosTelefono);
        model.addAttribute("estudiante", estudiante);
        return "views/detalleEstudiante";
    }
      }