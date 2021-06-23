package com.example.demo.controllers;

import com.example.demo.entity.Area;
import com.example.demo.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class AreaWS {
    //2 importando el repo par la busqueda en bd
    @Autowired
    AreaRepository areaRepository;

    @GetMapping(value="/area",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerListaAreas(){

        return new ResponseEntity(areaRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping(value="/area/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAreaxId(@RequestParam("id") Integer id){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            //int id = Integer.parseInt(idStr);
            Optional<Area> opt = areaRepository.findById(id);
            if (opt.isPresent()) {
                responseMap.put("estado", "ok");
                responseMap.put("area", opt.get());
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el area con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarProducto(
            @RequestBody Area area,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        areaRepository.save(area);
        if (fetchId) {
            responseMap.put("id", area.getIdarea());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }
    /* POST AREA CON USUARIO ID*/

    /*********************/
    @PutMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarArea(@RequestBody Area area) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (area.getIdarea() > 0) {
            Optional<Area> opt = areaRepository.findById(area.getIdarea());
            if (opt.isPresent()) {
                areaRepository.save(area);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "Area inexistente");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }





}
