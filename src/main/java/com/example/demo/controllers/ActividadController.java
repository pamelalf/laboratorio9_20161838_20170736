package com.example.demo.controllers;

import com.example.demo.entity.Actividad;
import com.example.demo.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

public class ActividadController {

    @Autowired
    ActividadRepository actividadRepository;

    @GetMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarActividades(){
        return new ResponseEntity(actividadRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/actividad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarActiviad(
            @RequestBody Actividad actividad,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        actividadRepository.save(actividad);
        if (fetchId) {
            responseMap.put("id", actividad.getIdactividad());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }

    @PutMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editarActividad(@RequestBody Actividad actividad){
        HashMap<String, Object> responseMap = new HashMap<>();
        Optional<Actividad> actividadOpt = actividadRepository.findById(actividad.getIdactividad());

        if(actividadOpt.isPresent()){
            //obtencion actividad
            Actividad actividad1 = actividadOpt.get();

            if(actividad1.getNombreactvidad()!= null){
                actividad1.getNombreactvidad(actividad.getNombreactvidad());
            }
            if(actividad1.getEstado() != null){
                actividad1.setEstado(actividad.getEstado());
            }
            if(actividad1.getDescripcion() != null){
                actividad1.setDescripcion(actividad.getDescripcion());
            }
            if(actividad1.getPeso() != null){
                actividad1.setPeso(actividad.getPeso());
            }
            if(actividad1.getUsuarioowner() != null){
                actividad1.setUsuarioowner(actividad.getUsuarioowner());
            }
            if(actividad1.getIdproyecto() != null){
                actividad1.setIdproyecto(actividad.getIdproyecto());
            }

            actividadRepository.save(actividad1);
            responseMap.put("estado",actividad1.getNombreactvidad()+" Actualizado");
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }else{
            responseMap.put("estado","error");
            responseMap.put("msg","La actividad no existe");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/actividad/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarActividad(@PathVariable("id") String idStr) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            int id = Integer.parseInt(idStr);
            if (actividadRepository.existsById(id)) {
                actividadRepository.deleteById(id);
                responseMap.put("estado", "Borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "No se encontró la actividad con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }



}
