/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.utils;

import com.jap.client.dto.Cempleado;
import com.jap.client.dto.SUsuario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author irvin_monterroza
 */
public class ClientUtils implements Serializable {

    private String httpSegment = "http://localhost:9090";
    private int id_empresa;
    private SUsuario usuario;
    private Cempleado empleado;

    public String getHttpSegment() {
        return httpSegment;
    }

    public void setHttpSegment(String httpSegment) {
        this.httpSegment = httpSegment;
    }

    public int getId_empresa() {
        FacesContext context = FacesContext.getCurrentInstance();
        return Integer.parseInt(String.valueOf(context.getExternalContext().getSessionMap().get("id_empresa")));
    }

    public void setId_empresa(int id_empresa) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("id_empresa", id_empresa);
    }

    public SUsuario getUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (SUsuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    public void setUsuario(SUsuario usuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuario", usuario);
    }

    public Cempleado getEmpleado() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (Cempleado) context.getExternalContext().getSessionMap().get("empleado");
    }

    public void setEmpleado(Cempleado empleado) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("empleado", empleado);
    }    

    public static String getMessegesBundles(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String message = bundle.getString(key);
        return message;
    }

    public void addmessageswarn(String messages) {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, ClientUtils.getMessegesBundles(messages), ""));
    }

    public void addmessagesok(String messages) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ClientUtils.getMessegesBundles(messages), ""));
    }

    public void addmessageserror(String messages) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ClientUtils.getMessegesBundles(messages), ""));
    }

    public void addsimplemessages(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }

    public void addsimplemessageswarn(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
    }

    public void addsimplemessageserror(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
    }

    public boolean validMail(String mail) {
        String pattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mail);
        return m.find();
    }

    public boolean ValidNullString(String field) {
        boolean nul = false;
        if (field == null || field.equals("")) {
            nul = true;
        }
        return nul;
    }

    public ClientResponse ClientRestFullResponse(String url, String param) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, param);
        return response;
    }

    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
