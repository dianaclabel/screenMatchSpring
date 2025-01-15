package com.aluracursos.screenMatchSpring.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Brenda","Maria","Fernanda","Eric","Genesys");

        nombres.stream().sorted().limit(2).filter(n -> n.startsWith("B")).map(n -> n.toUpperCase()).forEach(System.out::println);
    }
}
