/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.dto;

import java.io.Serializable;

/**
 *
 * @author irvin_monterroza
 */
public class Ccliente implements Serializable{
    private String  id;
    private String nombre;
    private String direccion;
    private String idDepto;
    private String descripcionDepto;
    private String idMunicipio;
    private String descripcionMunicipio;
    private String idCiudad;
    private String descripcionCiudad;
    private String registroFiscal;
    private String nit;
    private String giro;
    private String descripcionGiro;
    private String telefono1;
    private String telefono2;
    private String fax;
    private String limiteDeCredito;
    private String email;
    private String comentarios;
    private String percepcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(String idDepto) {
        this.idDepto = idDepto;
    }

    public String getDescripcionDepto() {
        return descripcionDepto;
    }

    public void setDescripcionDepto(String descripcionDepto) {
        this.descripcionDepto = descripcionDepto;
    }

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getDescripcionMunicipio() {
        return descripcionMunicipio;
    }

    public void setDescripcionMunicipio(String descripcionMunicipio) {
        this.descripcionMunicipio = descripcionMunicipio;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getDescripcionCiudad() {
        return descripcionCiudad;
    }

    public void setDescripcionCiudad(String descripcionCiudad) {
        this.descripcionCiudad = descripcionCiudad;
    }

    public String getRegistroFiscal() {
        return registroFiscal;
    }

    public void setRegistroFiscal(String registroFiscal) {
        this.registroFiscal = registroFiscal;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

   
    public String getDescripcionGiro() {
        return descripcionGiro;
    }

    public void setDescripcionGiro(String descripcionGiro) {
        this.descripcionGiro = descripcionGiro;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLimiteDeCredito() {
        return limiteDeCredito;
    }

    public void setLimiteDeCredito(String limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(String percepcion) {
        this.percepcion = percepcion;
    }
    
}
