package com.aluracursos.screenMatchSpring;

import com.aluracursos.screenMatchSpring.model.DatosEpisodio;
import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.aluracursos.screenMatchSpring.model.DatosTemporadas;
import com.aluracursos.screenMatchSpring.principal.Principal;
import com.aluracursos.screenMatchSpring.service.ConsumoAPI;
import com.aluracursos.screenMatchSpring.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatchSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSpringApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();



	}


}
