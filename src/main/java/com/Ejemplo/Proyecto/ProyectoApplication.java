package com.Ejemplo.Proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}


	@RestController
	public class HolaController {

		@GetMapping("/hola")
		public String hola() {
			return "¡Hola Mundo desde Spring Boot!";
		}

		@GetMapping("/saludo/{nombre}")
		public String saludo(@PathVariable String nombre) {
			return "¡Hola, " + nombre + "!";
		}

		}

	}
