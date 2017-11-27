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
public class SOpcion implements Serializable{
    private String id;
    private String descripcion;
    private String status;
    private String id_opc_principal;

    public String getId() {
        return id;
    }

    public String getId_opc_principal() {
        return id_opc_principal;
    }

    public void setId_opc_principal(String id_opc_principal) {
        this.id_opc_principal = id_opc_principal;
    }

    public void setId(String id) {
        this.id = id;
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
    
}
