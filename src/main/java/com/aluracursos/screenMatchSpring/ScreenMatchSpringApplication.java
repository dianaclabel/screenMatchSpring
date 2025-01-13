package com.aluracursos.screenMatchSpring;

import com.aluracursos.screenMatchSpring.model.DatosEpisodio;
import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.aluracursos.screenMatchSpring.model.DatosTemporadas;
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
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=597dbcb5");

		//var json = consumoApi.obtenerDatos("https://coffee.alexflipnote.dev/random.json");

		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);



		json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=597dbcb5");
		DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);



		List <DatosTemporadas> temporadas = new ArrayList<>();


		for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
			json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=597dbcb5");

			var datosTemporadas =  conversor.obtenerDatos(json, DatosTemporadas.class);

			temporadas.add(datosTemporadas);
		}

		temporadas.forEach(System.out::println);



	}


}
