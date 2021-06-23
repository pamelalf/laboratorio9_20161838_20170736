package com.example.demo.controllers;

import com.example.demo.entity.Area;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class UsuarioWS {


    @Autowired
    UsuarioRepository usuarioRepository;
    @GetMapping(value="/usuario",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerListaUsuarios(){

        return new ResponseEntity(usuarioRepository.findAll(), HttpStatus.OK);

    }

    @PostMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarProducto(
            @RequestBody Usuario usuario,
            @RequestParam(value = "fetchCorreo", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        usuarioRepository.save(usuario);
        if (fetchId){
            responseMap.put("id", usuario.getCorreo());
        }

        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }
    @PutMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUsuario(@RequestBody Usuario usuario) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (!usuario.getCorreo().isEmpty()) {
            Optional<Usuario> optional = usuarioRepository.findByCorreoEquals(usuario.getCorreo());
            if (optional.isPresent()) {
                usuarioRepository.save(usuario);
                responseMap.put("estado", "Actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El correo del usuario a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe indicar el correo del usuario");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/usuario/{correo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@PathVariable("correo") String correo) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (usuarioRepository.existsByCorreoEquals(correo)) {
            Optional<Usuario> optional = usuarioRepository.findByCorreoEquals(correo);
            usuarioRepository.delete(optional.get());
            responseMap.put("estado", "borrado exitoso");
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "No se encontró el usuario con correo: " + correo);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }
}
