package com.aluracursos.screenMatchSpring.principal;

import com.aluracursos.screenMatchSpring.model.DatosEpisodio;
import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.aluracursos.screenMatchSpring.model.DatosTemporadas;
import com.aluracursos.screenMatchSpring.model.Episodio;
import com.aluracursos.screenMatchSpring.service.ConsumoAPI;
import com.aluracursos.screenMatchSpring.service.ConvierteDatos;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        //equalsIgnoreCase nos permite hacer una comparacion sin tener en en cuenta palabras mayusculas o minusculas devuelve un boolean
        //sorted ordena de menor a mayor, comparator.comparing permite por evalucion, reversed nos traerá los datos de menor a mayor
        /*System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A") )
                .peek(e -> System.out.println("Primer filtro (N/A)" + e ))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Segundo filtro (M>m)" + e ))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Tercer filtro Mayuscula (M>m)" + e))
                .limit(5)
                .forEach(System.out::println);

         */



        //convirtiendo los Datos a una lista de tipo Episodio.
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());
        //episodios.forEach(System.out::println);

        // Busqueda de episodios a partir de X año
        /*
        System.out.println("Por favor indica el año del cual deseas ver los episodios");

        var fecha = teclado.nextInt();
        teclado.nextLine();
        LocalDate fechaDeBusqueda = LocalDate.of(fecha, 1 , 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");+/
/*
        episodios.stream().filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaDeBusqueda))
                .forEach( e -> System.out.println(
                        "Temporada "+ e.getTemporada() +
                                "Episodio "+ e.getTitulo() +
                                " Fecha de Lanzamiento " + e.getFechaDeLanzamiento().format(dtf)
                ));

*/

        // Busca episodios por un pedazo del titulo
        //findFirst va a encontrar la primera coincidencia

        /*
        System.out.println("Por favor escriba el titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()){
            System.out.println("Episodio buscado");
            System.out.println("los datos son: " + episodioBuscado.get());
        }else {
            System.out.println("Episodio no encontrado ");
        }
         */

        Map<Integer,Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion) ));

        System.out.println(evaluacionesPorTemporada);





    }
}
