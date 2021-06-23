package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idActividad")
    private int idactividad;
    private String nombreactvidad;
    private String descripcion;
    private int idproyecto;
    @Column(name="usuario_owner")
    private String usuarioowner;
    private float peso;
    private int estado;

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }

    public String getNombreactvidad() {
        return nombreactvidad;
    }

    public void setNombreactvidad(String nombreactvidad) {
        this.nombreactvidad = nombreactvidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getUsuarioowner() {
        return usuarioowner;
    }

    public void setUsuarioowner(String usuarioowner) {
        this.usuarioowner = usuarioowner;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
