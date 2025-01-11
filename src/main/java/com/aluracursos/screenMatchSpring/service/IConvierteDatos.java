package com.aluracursos.screenMatchSpring.service;

public interface IConvierteDatos {
    //Los tipos de datos genericos se ven asi <T> T, no se sabe que va retornar;
    //Pasamos una clase que tambien es de tipo generica
   <T> T obtenerDatos(String json, Class<T> clase);

}
