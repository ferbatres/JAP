package com.jap.client.mbean;

import com.jap.client.dto.Ccliente;
import com.jap.client.utils.ClientUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fernando_batres
 */
@ManagedBean(name = "clienteMBean")
@ViewScoped
public class CclienteMBean extends ClientUtils implements Serializable {

    private Ccliente cliente;
    private Ccliente selectedCliente;
    private Ccliente clienteOE;
    private List<Ccliente> filteredCliente;
    private List<Ccliente> list;

    public CclienteMBean() {
        selectedCliente = new Ccliente();
        clienteOE = new Ccliente();
        filteredCliente = new ArrayList<Ccliente>();
    }

    public List<Ccliente> clienteList() {
        System.out.println("llenando clienteList");
        list = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/fccliente";
        try {
            String result = ClientRestFullResponse(url, param).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("dato");
            for (int i = 0; i < arr.length(); i++) {
                Ccliente c = new Ccliente();
                c.setId(arr.getJSONObject(i).get("id").toString());
                c.setNombre(arr.getJSONObject(i).get("nombre").toString());
                c.setDireccion(arr.getJSONObject(i).get("direccion").toString());
                c.setIdDepto(arr.getJSONObject(i).get("idDepto").toString());
                c.setDescripcionDepto(arr.getJSONObject(i).get("descripcionDepto").toString());
                c.setIdMunicipio(arr.getJSONObject(i).get("idMunicipio").toString());
                c.setDescripcionMunicipio(arr.getJSONObject(i).get("descripcionMunicipio").toString());
                c.setIdCiudad(arr.getJSONObject(i).get("idCiudad").toString());
                c.setDescripcionCiudad(arr.getJSONObject(i).get("descripcionCiudad").toString());
                c.setRegistroFiscal(arr.getJSONObject(i).get("registroFiscal").toString());
                c.setNit(arr.getJSONObject(i).get("nit").toString());
                c.setGiro(arr.getJSONObject(i).get("giro").toString());
                c.setDescripcionGiro(arr.getJSONObject(i).get("descripcionGiro").toString());
                c.setTelefono1(arr.getJSONObject(i).get("telefono1").toString());
                c.setTelefono2(arr.getJSONObject(i).get("telefono2").toString());
                c.setFax(arr.getJSONObject(i).get("fax").toString());
                c.setLimiteDeCredito(arr.getJSONObject(i).get("limiteDeCredito").toString());
                c.setEmail(arr.getJSONObject(i).get("email").toString());
                c.setComentarios(arr.getJSONObject(i).get("comentarios").toString());
                c.setPercepcion(arr.getJSONObject(i).get("percepcion").toString());
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save() {
        try {
            JSONObject param = new JSONObject();
            param.put("id", this.cliente.getId());
            param.put("nombre", this.cliente.getNombre());
            param.put("direccion", this.cliente.getDireccion());
            param.put("idDepto", this.cliente.getIdDepto());
            param.put("idMunicipio", this.cliente.getIdMunicipio());
            param.put("idCiudad", this.cliente.getIdCiudad());
            param.put("registroFiscal", this.cliente.getRegistroFiscal());
            param.put("nit", this.cliente.getNit());
            param.put("giro", this.cliente.getGiro());
            param.put("telefono1", this.cliente.getTelefono1());
            param.put("telefono2", this.cliente.getTelefono2());
            param.put("fax", this.cliente.getFax());
            param.put("limiteDeCredito", this.cliente.getLimiteDeCredito());
            param.put("email", this.cliente.getEmail());
            param.put("comentarios", this.cliente.getComentarios());
            param.put("percepcion", this.cliente.getPercepcion());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/scliente";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            cleanObjects();
            nextIdCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectClienteFromDialog() {
        System.out.println("entro selectClienteFromDialog");
        clienteOE =  new Ccliente();
        this.clienteOE.setId(cliente.getId());
        this.clienteOE.setComentarios(cliente.getComentarios());
        this.clienteOE.setDireccion(cliente.getDireccion());
        this.clienteOE.setEmail(cliente.getEmail());
        this.clienteOE.setFax(cliente.getFax());
        this.clienteOE.setIdCiudad(cliente.getIdCiudad());
        this.clienteOE.setIdDepto(cliente.getIdDepto());
        this.clienteOE.setDescripcionDepto(cliente.getDescripcionDepto());
        this.clienteOE.setGiro(cliente.getGiro());
        this.clienteOE.setIdMunicipio(cliente.getIdMunicipio());
        this.clienteOE.setDescripcionMunicipio(cliente.getDescripcionMunicipio());
        this.clienteOE.setLimiteDeCredito(cliente.getLimiteDeCredito());
        this.clienteOE.setNombre(cliente.getNombre());
        this.clienteOE.setNit(cliente.getNit());
        this.clienteOE.setPercepcion(cliente.getPercepcion());
        this.clienteOE.setRegistroFiscal(cliente.getRegistroFiscal());
        this.clienteOE.setTelefono1(cliente.getTelefono1());
        this.clienteOE.setTelefono2(cliente.getTelefono2());
    }

    private void nextIdCliente() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        this.cliente.setId(obj.get("dato").toString());

    }

    public void cleanObjects() {
        //System.out.println("entro cleanObjects");
        this.cliente = new Ccliente();
        clienteList();
        nextIdCliente();
    }
    
    public void cleanClienteForm(){
        clienteOE = null;
    }

    /*
     *   Accesores
     */
    public Ccliente getCliente() {
        return cliente;
    }

    public void setCliente(Ccliente cliente) {
        this.cliente = cliente;
    }

    public Ccliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Ccliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public Ccliente getClienteOE() {
        return clienteOE;
    }

    public void setClienteOE(Ccliente clienteOE) {
        this.clienteOE = clienteOE;
    }

    public List<Ccliente> getFilteredCliente() {
        return filteredCliente;
    }

    public void setFilteredCliente(List<Ccliente> filteredCliente) {
        this.filteredCliente = filteredCliente;
    }

    public List<Ccliente> getList() {
        return list;
    }

    public void setList(List<Ccliente> list) {
        this.list = list;
    }

}
