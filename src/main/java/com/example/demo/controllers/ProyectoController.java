package com.example.demo.controllers;

import com.example.demo.entity.Actividad;
import com.example.demo.entity.Proyecto;
import com.example.demo.repository.ActividadRepository;
import com.example.demo.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class ProyectoController {

    @Autowired
    ProyectoRepository proyectosRepository;
    @Autowired
    ActividadRepository actividadRepository;

    @GetMapping(value = "/proyecto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ObtenerlistaProyectos() {
        return new ResponseEntity(proyectosRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/proyecto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerProyectos(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Proyecto> optional = proyectosRepository.findById(id);
            if (optional.isPresent()) {
                responseMap.put("estado", "ok");
                responseMap.put("proyecto", optional.get());
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el proyecto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }

        } catch (NumberFormatException numberFormatException) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/proyecto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveProyecto(
            @RequestBody Proyecto proyecto,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        proyectosRepository.save(proyecto);
        if (fetchId) {
            responseMap.put("id", proyecto.getIdproyecto());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }



    @GetMapping(value = "/proyectoConActividades/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerProyectoconActividades(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Proyecto> opt = proyectosRepository.findById(id);
            List<Actividad> actividadList= actividadRepository.findByIdproyecto(id);

            if (opt.isPresent() && actividadList.size()!=0) {
                responseMap.put("estado", "ok");
                responseMap.put("proyecto", opt.get());
                responseMap.put("listaActividades", actividadList);
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el proyecto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/proyecto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarProyecto(@RequestBody Proyecto proyecto) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (proyecto.getIdproyecto() > 0) {
            Optional<Proyecto> opt = proyectosRepository.findById(proyecto.getIdproyecto());
            if (opt.isPresent()) {
                Proyecto proyecto_1 = opt.get();
                if (proyecto.getNombreproyecto() != null &&
                        !proyecto_1.getNombreproyecto().equals(proyecto.getNombreproyecto())) {
                    proyecto_1.setNombreproyecto(proyecto.getNombreproyecto());
                }
                if (proyecto.getUsuarioowner() != null &&
                        proyecto_1.getUsuarioowner().equals(proyecto.getUsuarioowner())) {
                    proyecto_1.setUsuarioowner(proyecto.getUsuarioowner());
                }
                if (proyecto.getIdproyecto() != 0 &&
                        proyecto_1.getIdproyecto() != proyecto.getIdproyecto()) {
                    proyecto_1.setIdproyecto(proyecto.getIdproyecto());
                }
                proyectosRepository.save(proyecto_1);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El id del proyecto a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(value = "/proyecto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarProyecto(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try { //try
            int id = Integer.parseInt(idStr);
            if (proyectosRepository.existsById(id)) {
                proyectosRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el proyecto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException numberFormatException) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }






}
