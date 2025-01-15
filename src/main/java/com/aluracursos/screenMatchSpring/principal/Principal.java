package com.aluracursos.screenMatchSpring.principal;

import com.aluracursos.screenMatchSpring.model.DatosEpisodio;
import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.aluracursos.screenMatchSpring.model.DatosTemporadas;
import com.aluracursos.screenMatchSpring.service.ConsumoAPI;
import com.aluracursos.screenMatchSpring.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=597dbcb5";

    private  ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");

        //Busca los datos generales de la serie
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE +  nombreSerie.replace(" ", "+")+ API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);


        //Buscar los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+ "&Season="+i+API_KEY);
            var datosTemporadas =  conversor.obtenerDatos(json, DatosTemporadas.class);

            temporadas.add(datosTemporadas);
        }

        //temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios
        /*
        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodioTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodioTemporada.size(); j++) {
                System.out.println(episodioTemporada.get(j).titulo());
            }

        }*/

        //Mejoria usando funciones lamda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        //convertir todas las informaciones a una lista del tipo datosEpisodio


        //Collectors.toList nos permite crear una lista mutable
        //toList crea lista inmutable
        //stream nos permite usar metodo lambda
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios()
                .stream()).collect(Collectors.toList());


        //Top 5 episodios
        // filter nos permite hacer un filtro que excluyan los N/A
        //sorted ordena de menor a mayor, comparator.comparing permite por evalucion, reversed nos traerá los datos de menor a mayor
        System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A") )
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);




    }
}
