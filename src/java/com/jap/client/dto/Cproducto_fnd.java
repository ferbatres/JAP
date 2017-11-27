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
public class Cproducto_fnd implements Serializable{
        String id   = "";
        String id_marca = "";
        String id_modelo = "";
        String descripcion = "";
        String id_categoria = "";
        String stock = "";
        String servicio = "";
        String stock_minimo = "";
        String stock_maximo = "";
        String suspendido = "";
        String costo_compra = "";
        String costo_fob = "";
        String costo_contable = "";
        String ultimo_costo_s_impuesto = "";
        String ultimo_costo_c_impuesto = "";
        String costo_prom_s_impuesto = "";
        String costo_prom_c_impuesto = "";
        String costo_anterior_c_impuesto = "";
        String utilidad1 = "";
        String precio1 = "";
        String utilidad2 = "";
        String precio2 = "";
        String utildad3 = "";
        String precio3 = "";
        String oem = "";
        String desc_modelo="";
        String desc_marca="";
        String desc_categoria="";
        String codigo="";
        String fecha_recepcion="";
       

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_marca() {
        return id_marca;
    }

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(String stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getStock_maximo() {
        return stock_maximo;
    }

    public void setStock_maximo(String stock_maximo) {
        this.stock_maximo = stock_maximo;
    }

    public String getSuspendido() {
        return suspendido;
    }

    public void setSuspendido(String suspendido) {
        this.suspendido = suspendido;
    }

    public String getDesc_modelo() {
        return desc_modelo;
    }

    public void setDesc_modelo(String desc_modelo) {
        this.desc_modelo = desc_modelo;
    }

    public String getDesc_marca() {
        return desc_marca;
    }

    public void setDesc_marca(String desc_marca) {
        this.desc_marca = desc_marca;
    }

    public String getDesc_categoria() {
        return desc_categoria;
    }

    public void setDesc_categoria(String desc_categoria) {
        this.desc_categoria = desc_categoria;
    }

    public String getCosto_compra() {
        return costo_compra;
    }

    public void setCosto_compra(String costo_compra) {
        this.costo_compra = costo_compra;
    }

    public String getCosto_fob() {
        return costo_fob;
    }

    public void setCosto_fob(String costo_fob) {
        this.costo_fob = costo_fob;
    }

    public String getCosto_contable() {
        return costo_contable;
    }

    public void setCosto_contable(String costo_contable) {
        this.costo_contable = costo_contable;
    }

    public String getUltimo_costo_s_impuesto() {
        return ultimo_costo_s_impuesto;
    }

    public void setUltimo_costo_s_impuesto(String ultimo_costo_s_impuesto) {
        this.ultimo_costo_s_impuesto = ultimo_costo_s_impuesto;
    }

    public String getUltimo_costo_c_impuesto() {
        return ultimo_costo_c_impuesto;
    }

    public void setUltimo_costo_c_impuesto(String ultimo_costo_c_impuesto) {
        this.ultimo_costo_c_impuesto = ultimo_costo_c_impuesto;
    }

    public String getCosto_prom_s_impuesto() {
        return costo_prom_s_impuesto;
    }

    public void setCosto_prom_s_impuesto(String costo_prom_s_impuesto) {
        this.costo_prom_s_impuesto = costo_prom_s_impuesto;
    }

    public String getCosto_prom_c_impuesto() {
        return costo_prom_c_impuesto;
    }

    public void setCosto_prom_c_impuesto(String costo_prom_c_impuesto) {
        this.costo_prom_c_impuesto = costo_prom_c_impuesto;
    }

    public String getCosto_anterior_c_impuesto() {
        return costo_anterior_c_impuesto;
    }

    public void setCosto_anterior_c_impuesto(String costo_anterior_c_impuesto) {
        this.costo_anterior_c_impuesto = costo_anterior_c_impuesto;
    }

    public String getUtilidad1() {
        return utilidad1;
    }

    public void setUtilidad1(String utilidad1) {
        this.utilidad1 = utilidad1;
    }

    public String getPrecio1() {
        return precio1;
    }

    public void setPrecio1(String precio1) {
        this.precio1 = precio1;
    }

    public String getUtilidad2() {
        return utilidad2;
    }

    public void setUtilidad2(String utilidad2) {
        this.utilidad2 = utilidad2;
    }

    public String getPrecio2() {
        return precio2;
    }

    public void setPrecio2(String precio2) {
        this.precio2 = precio2;
    }

    public String getUtildad3() {
        return utildad3;
    }

    public void setUtildad3(String utildad3) {
        this.utildad3 = utildad3;
    }

    public String getPrecio3() {
        return precio3;
    }

    public void setPrecio3(String precio3) {
        this.precio3 = precio3;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }
    
}
