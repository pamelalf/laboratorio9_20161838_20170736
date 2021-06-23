package com.example.demo.AreaController;

import com.example.demo.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        return new ResponseEntity(areaRepository.findById(id), HttpStatus.OK);

    }


}
