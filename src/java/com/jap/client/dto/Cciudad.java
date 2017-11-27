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
public class Cciudad implements Serializable{
    private String id;
    private String idmunicipio;
    private String iddepto;
    private String descripcion;
    private String status;
    private String descripciondepto;
    private String descripcionmunicpio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(String idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getIddepto() {
        return iddepto;
    }

    public void setIddepto(String iddepto) {
        this.iddepto = iddepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripciondepto() {
        return descripciondepto;
    }

    public void setDescripciondepto(String descripciondepto) {
        this.descripciondepto = descripciondepto;
    }

    public String getDescripcionmunicpio() {
        return descripcionmunicpio;
    }

    public void setDescripcionmunicpio(String descripcionmunicpio) {
        this.descripcionmunicpio = descripcionmunicpio;
    }
    
    
    
}
