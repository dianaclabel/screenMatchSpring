package com.aluracursos.screenMatchSpring.service;

import com.aluracursos.screenMatchSpring.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    //Deserialización: Convertir una cadena JSON a un objeto Java.
    public <T> T obtenerDatos(String json, Class<T> clase) {

        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
