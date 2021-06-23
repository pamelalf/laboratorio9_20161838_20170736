package com.example.demo.repository;

import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    //Uso en UsuarioWS
    Optional<Usuario> findByCorreoEquals(String correo);
    Boolean existsByCorreoEquals(String correo);



}
