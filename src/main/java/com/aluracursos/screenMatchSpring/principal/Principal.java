package com.aluracursos.screenMatchSpring.principal;

import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.aluracursos.screenMatchSpring.model.DatosTemporadas;
import com.aluracursos.screenMatchSpring.service.ConsumoAPI;
import com.aluracursos.screenMatchSpring.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        temporadas.forEach(System.out::println);



    }
}
