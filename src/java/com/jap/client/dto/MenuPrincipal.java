/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.dto;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author irvin_monterroza
 */
public class MenuPrincipal implements Serializable{
    private String descripcion ;
    private String id;
    private String id_rol;
    private String menu_icon;
    private List<SOpcionMenu> opcionesSubMenu;

    public List<SOpcionMenu> getOpcionesSubMenu() {
        return opcionesSubMenu;
    }

    public void setOpcionesSubMenu(List<SOpcionMenu> opcionesSubMenu) {
        this.opcionesSubMenu = opcionesSubMenu;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }
    

   

    public String getDescripcion() {
        return descripcion;
    }

   

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }
            
    
}
