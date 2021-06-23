package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproyecto;
    @Column(name="nombreProyecto")
    private String nombreproyecto;
    @Column(name="usuario_owner")
    private String usuarioowner;

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getNombreproyecto() {
        return nombreproyecto;
    }

    public void setNombreproyecto(String nombreproyecto) {
        this.nombreproyecto = nombreproyecto;
    }

    public String getUsuarioowner() {
        return usuarioowner;
    }

    public void setUsuarioowner(String usuarioowner) {
        this.usuarioowner = usuarioowner;
    }
}
