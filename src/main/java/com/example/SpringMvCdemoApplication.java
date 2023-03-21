package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.entities.Estudiante.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class SpringMvCdemoApplication implements CommandLineRunner {

	@Autowired
	private FacultadService facultadService;

	@Autowired
	private EstudianteService estudianteService;

	@Autowired
	private TelefonoService telefonoService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMvCdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/**
		 * Método para agregar registros de muestra para Facultad (crear y añadir
		 * facultades),
		 * Estudiante (crear y añadir estudiantes) y Telefono (crear y añadir
		 * telefonos):
		 */

		// Despues del siguiente codigo, aparecera el nombre de mis facultades en mysql
		facultadService.save(
				Facultad.builder()
						.nombre("Informatica")
						.build());

		facultadService.save(
				Facultad.builder()
						.nombre("Biologia")
						.build());

		estudianteService.save(Estudiante.builder() //mi clase Estudiante
		        .id(1) // hay que meterle el id del estudiante que estoy añadiendo o no funciona 
				.nombre("Elisabet")
				.primerApellido("Agulló")
				.segundoApellido("García")
				.fechaAlta(LocalDate.of(2018, Month.APRIL, 2))
				.fechaNacimiento(LocalDate.of(2000, Month.APRIL, 20))
				.genero(Genero.MUJER)
				.beca(98765432.00)
				.facultad(facultadService.findById(1))
				.build());

			// quiero que mi estudiante tenga 2 telefonos	
			telefonoService.save(Telefono.builder()
			.id(2)
			.numero("968567213")
			.estudiante(estudianteService.findById(1))
			.build()
			);

	}
}
