/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.dto;

import java.io.Serializable;
import org.primefaces.component.commandlink.CommandLink;

/**
 *
 * @author irvin_monterroza
 */
public class SOpcionMenu implements Serializable{
    private String id;
    private String descripcion;
    private String status;
    private String props_update;
    private String props_onclick;
    private String props_actionlistener;
    private String id_opc_principal;
    private CommandLink commandlinkopciones;

    

    public String getId() {
        return id;
    }

    public String getProps_update() {
        return props_update;
    }

    public void setProps_update(String props_update) {
        this.props_update = props_update;
    }

    public String getProps_onclick() {
        return props_onclick;
    }

    public void setProps_onclick(String props_onclick) {
        this.props_onclick = props_onclick;
    }

    public String getProps_actionlistener() {
        return props_actionlistener;
    }

    public void setProps_actionlistener(String props_actionlistener) {
        this.props_actionlistener = props_actionlistener;
    }

    public CommandLink getCommandlinkopciones() {
        return commandlinkopciones;
    }

    public void setCommandlinkopciones(CommandLink commandlinkopciones) {
        this.commandlinkopciones = commandlinkopciones;
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
