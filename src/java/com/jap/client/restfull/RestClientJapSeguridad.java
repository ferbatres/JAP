/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.restfull;

import com.jap.client.dto.Cempleado;
import com.jap.client.dto.Cempresa;
import com.jap.client.dto.MenuPrincipal;
import com.jap.client.dto.SOpcion;
import com.jap.client.dto.SOpcionMenu;
import com.jap.client.dto.SOpcionPrincipal;
import com.jap.client.dto.SRol;
import com.jap.client.dto.SUsuario;
import com.jap.client.utils.ClientUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.model.DualListModel;

/**
 *
 * @author irvin_monterroza
 */
@ManagedBean
@SessionScoped
public class RestClientJapSeguridad extends ClientUtils implements Serializable {

    /**
     * Creates a new instance of RestClientJapSeguridad
     */
    private boolean http_sec_acces = false;
    private CommandLink link_p;
    private boolean logueado = false;
    private List<Cempresa> CempresaList;
    private String selectedIdEmpresa;

    private void loadCempresa() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempresa/fcempresa";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CempresaList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cempresa c = new Cempresa();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CempresaList.add(c);

        }

    }

    public RestClientJapSeguridad() {
        loadCempresa();
        pageSopcion = "/seguridad/s_opcion.xhtml";
        pageSrol = "/seguridad/s_rol.xhtml";
        pageSopcionrol = "/seguridad/s_opcion_rol.xhtml";
        pageSusuario = "/seguridad/s_usuario.xhtml";
        pageSrolusuario = "/seguridad/s_rol_usuario.xhtml";
        pageOpcionPrincipal = "/seguridad/s_opcion_principal.xhtml";
        opcionDualListModel = new DualListModel<>(opcionSource, opcionTarget);
        rolDualListModel = new DualListModel<>(rolSource, rolTarget);
        opcionesmenu = new ArrayList<>();
    }

    public void action(String actionListenes) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        createMethodExpression(actionListenes, null, (Class<?>) null).invoke(facesContext.getELContext(), null);
    }

    public MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), expression, returnType, parameterTypes);
    }

    public void linkBinding() {
        System.out.println("sirve el link");

    }
    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad  OPciones :::::::::::::......................*/

    private boolean SopcionEdit = true;
    private String SopcionId;
    private String SopcionDescripcion;
    private String SopcionStatus;
    private String SopcionMenuIcon;
    private SOpcion SelectedSOpcionDTO;
    private List<SOpcion> SopcionList;
    private String id_oprcion_principal;
    private List<SOpcionPrincipal> cbxOpcionPrincipalList;
    private String pageSopcion = "";

    private void loadSOpcionPrincpalCbx() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionprincipal/fsopcionprincipal";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        cbxOpcionPrincipalList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SOpcionPrincipal c = new SOpcionPrincipal();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setMenu_icon(arr.getJSONObject(i).get("menuIcon").toString());
            cbxOpcionPrincipalList.add(c);

        }

    }

    private void loadSOpcion() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/fsopcion";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        SopcionList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SOpcion c = new SOpcion();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setId_opc_principal(arr.getJSONObject(i).get("idOpcPrincipal").toString());
            SopcionList.add(c);

        }

    }
    private String pagesLog = "";

    public void logOutJap() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            logueado = false;
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/logout");
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(RestClientJapSeguridad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestClientJapSeguridad.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void nextIdOpcion() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/nextid";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setSopcionId(obj.get("dato").toString());

    }

    public void ClearOpcion() {
        setSopcionDescripcion("");
        setSopcionId("");
        setSopcionStatus("0");
        setId_oprcion_principal("0");
        SelectedSOpcionDTO = new SOpcion();
        nextIdOpcion();
        SopcionEdit = true;
    }

    public void selectedRowOpcion() {
        setSopcionDescripcion(SelectedSOpcionDTO.getDescripcion());
        setSopcionId(SelectedSOpcionDTO.getId());
        setSopcionStatus(SelectedSOpcionDTO.getStatus());
        setId_oprcion_principal(SelectedSOpcionDTO.getId_opc_principal());
        SopcionEdit = false;
    }

    public void cancelarOpcion() {
        ClearOpcion();
    }

    public void loadSopcionLink() {
        loadSOpcion();
        nextIdOpcion();
        ClearOpcion();
        loadSOpcionPrincpalCbx();
    }

    public void DSopcion() {
        JSONObject param = new JSONObject();
        param.put("id", getSopcionId());
        param.put("id_empresa", getId_empresa());
        param.put("id_opc_principal", getId_oprcion_principal());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/dsopcion";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadSOpcion();
        ClearOpcion();

    }

    public void SSopcion() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getSopcionDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }

        if (ValidNullString(getId_oprcion_principal())) {
            validate = true;
            addsimplemessageserror("Debe de Seleccionar la opcion princopal a la cual se asociara la opcion hija.");
        }
        if (ValidNullString(getSopcionStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getSopcionId());
            param.put("descripcion", getSopcionDescripcion());
            param.put("status", getSopcionStatus());
            param.put("id_opc_principal", getId_oprcion_principal());
            param.put("id_empresa", getId_empresa());
            String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/ssopcion";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadSOpcion();
            ClearOpcion();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad  Roles :::::::::::::......................*/
    private boolean SrolEdit = true;
    private String SrolId;
    private String SrolDescripcion;
    private String SrolStatus;
    private SRol SelectedSRolDTO;
    private List<SRol> SrolList;
    private String pageSrol = "";

    private void loadSRol() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/fsrol";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        SrolList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SRol c = new SRol();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            SrolList.add(c);

        }

    }

    private void nextIdRol() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setSrolId(obj.get("dato").toString());

    }

    public void ClearRol() {
        setSrolDescripcion("");
        setSrolId("");
        setSrolStatus("0");
        SelectedSRolDTO = new SRol();
        nextIdRol();
        SrolEdit = true;
    }

    public void selectedRowRol() {
        setSrolDescripcion(SelectedSRolDTO.getDescripcion());
        setSrolId(SelectedSRolDTO.getId());
        setSrolStatus(SelectedSRolDTO.getStatus());
        SrolEdit = false;
    }

    public void cancelarRol() {
        ClearRol();
    }

    public void loadSrolLink() {
        loadSRol();
        nextIdRol();
        ClearRol();
    }

    public void DSrol() {
        JSONObject param = new JSONObject();
        param.put("id", getSrolId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/dsrol";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadSRol();
        ClearRol();

    }

    public void SSrol() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getSrolDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getSrolStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getSrolId());
            param.put("descripcion", getSrolDescripcion());
            param.put("status", getSrolStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/srol/ssrol";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadSRol();
            ClearRol();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad Opciones y  Roles :::::::::::::......................*/
    private boolean SopcionrolEdit = true;
    private String pageSopcionrol = "";
    private List<SRol> cbxRolList;
    private String opcionrolidRol;
    private DualListModel<SOpcion> opcionDualListModel;
    private List<SOpcion> opcionSource = new ArrayList<>();
    private List<SOpcion> opcionTarget = new ArrayList<>();

    public void cancelarOpcionRol() {
        System.out.println("opcionSource-->");
        System.out.println("opcionTarget-->");
    }

    public void saveListOpcionesRol() {
        JSONObject paramUpdate = new JSONObject();
        JSONObject paramDelete = new JSONObject();
        if (!ValidNullString(getOpcionrolidRol())) {

            paramDelete.put("id_rol", getOpcionrolidRol());
            paramDelete.put("id_empresa", getId_empresa());
            String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionrol/dsopcionrolall";
            String result = ClientRestFullResponse(url, paramDelete.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject();
            for (SOpcion up : opcionDualListModel.getTarget()) {

                paramUpdate.put("id_opcion", up.getId());
                paramUpdate.put("id_rol", getOpcionrolidRol());
                paramUpdate.put("id_empresa", getId_empresa());
                paramUpdate.put("id_opcion_principal", up.getId_opc_principal());

                String urlUpdate = getHttpSegment() + "/WsRestFullJap/jap/sopcionrol/ssopcionrol";
                String resultUpdate = ClientRestFullResponse(urlUpdate, paramUpdate.toString()).getEntity(String.class);
                obj = new JSONObject(resultUpdate);

            }
            addsimplemessages((obj.length() != 0 ? obj.get("dato").toString() : "Opciones Registradas con Exito"));
            clearOpcionRol();
        } else {
            addsimplemessageswarn("Debe de Seleccionar un Rol");
            clearOpcionRol();
        }

    }

    public void clearOpcionRol() {
        setOpcionrolidRol("");
        opcionSource = new ArrayList<>();
        opcionTarget = new ArrayList<>();
        opcionDualListModel = new DualListModel<>(opcionSource, opcionTarget);

    }

    public void chancecbRole() {
        loadDualListRolsource();
        loadDualListRoltaget();
    }

    private void loadCbxRol() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/fsrol";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        cbxRolList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SRol c = new SRol();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            cbxRolList.add(c);

        }

    }

    //las que no tiene
    public void loadDualListRolsource() {
        JSONObject param = new JSONObject();
        param.put("id_role", getOpcionrolidRol());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/rolsinopciones";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        opcionSource = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SOpcion c = new SOpcion();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setId_opc_principal(arr.getJSONObject(i).get("idOpcPrincipal").toString());
            opcionSource.add(c);

        }
        opcionDualListModel = new DualListModel<>(opcionSource, opcionTarget);

    }

    // las que ya tiene
    public void loadDualListRoltaget() {
        JSONObject param = new JSONObject();
        param.put("id_role", getOpcionrolidRol());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcion/rolconopciones";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        opcionTarget = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SOpcion c = new SOpcion();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setId_opc_principal(arr.getJSONObject(i).get("idOpcPrincipal").toString());
            opcionTarget.add(c);

        }
        opcionDualListModel = new DualListModel<>(opcionSource, opcionTarget);

    }

    public void loadSopcionrolLink() {
        clearOpcionRol();
        loadCbxRol();

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad  Usuario :::::::::::::......................*/
    private boolean SusuarioEdit = true;
    private String SusuarioUsr;
    private String SusuarioNombre;
    private String SusuarioStatus;
    private String SusuarioClave;
    private String SusuarioEmail;
    private String SusuarioIdempleado;
    private SUsuario SelectedSUsuarioDTO;
    private List<SUsuario> SusuarioList;
    private String pageSusuario = "";

    private List<Cempleado> CtipoEmpleadoList;

    private void loadCtipoEmpleado() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempleado/fcempleado";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoEmpleadoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cempleado c = new Cempleado();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("nombre").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoEmpleadoList.add(c);

        }

    }

    private void loadSUsuario() {
        loadCtipoEmpleado();
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/susuario/fsusuario";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        SusuarioList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SUsuario c = new SUsuario();
            c.setUsr(arr.getJSONObject(i).get("usr").toString());
            c.setClave(arr.getJSONObject(i).get("clave").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setEmail(arr.getJSONObject(i).get("email").toString());
            c.setNombre(arr.getJSONObject(i).get("nombre").toString());
            c.setEmpleado(arr.getJSONObject(i).get("id_empleado").toString());
            SusuarioList.add(c);

        }

    }

    public void ClearUsuario() {
        setSusuarioClave("");
        setSusuarioEmail("");
        setSusuarioStatus("0");
        setSusuarioNombre("");
        setSusuarioUsr("");
        setSusuarioIdempleado("");
        SelectedSUsuarioDTO = new SUsuario();
        SusuarioEdit = true;
    }

    public void selectedRowUsuario() {
        setSusuarioClave(SelectedSUsuarioDTO.getClave());
        setSusuarioEmail(SelectedSUsuarioDTO.getEmail());
        setSusuarioNombre(SelectedSUsuarioDTO.getNombre());
        setSusuarioUsr(SelectedSUsuarioDTO.getUsr());
        setSusuarioStatus(SelectedSUsuarioDTO.getStatus());
        setSusuarioIdempleado(SelectedSUsuarioDTO.getEmpleado());
        SusuarioEdit = false;
    }

    public void cancelarUsuario() {
        ClearUsuario();
    }

    public void loadSusuarioLink() {
        loadSUsuario();
        ClearUsuario();
    }

    public void DSusuario() {
        JSONObject param = new JSONObject();
        param.put("usr", getSusuarioUsr());
        param.put("id_empresa", getId_empresa());
        param.put("id_empleado", getSusuarioIdempleado());
        String url = getHttpSegment() + "/WsRestFullJap/jap/susuario/dsusuario";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadSUsuario();
        ClearUsuario();

    }
    private boolean usr_new_update = true;

    public void SSusuario() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getSusuarioUsr())) {
            validate = true;
            addsimplemessageserror("Campo Usuario es requerido");
        }
        if (ValidNullString(getSusuarioNombre())) {
            validate = true;
            addsimplemessageserror("Campo Nombre es requerido");
        }
        if (ValidNullString(getSusuarioClave())) {
            validate = true;
            addsimplemessageserror("Campo contraseña es requerido");
        }
        if (ValidNullString(getSusuarioStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar estado");
        }
        if (ValidNullString(getSusuarioIdempleado())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar Empleado");
        }
        if (!validate) {
            param.put("usr", getSusuarioUsr());
            param.put("nombre", getSusuarioNombre());
            param.put("status", getSusuarioStatus());
            param.put("email", getSusuarioEmail());
            param.put("clave", getSusuarioClave());
            param.put("id_empresa", getId_empresa());
            param.put("id_empleado", getSusuarioIdempleado());

            String url = getHttpSegment() + "/WsRestFullJap/jap/susuario/ssusuario";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadSUsuario();
            ClearUsuario();

        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad ROLES  Usuario :::::::::::::......................*/
    private boolean SrolusuarioEdit = true;
    private String pageSrolusuario = "";
    private List<SUsuario> tableUsuarios;
    private SUsuario selectedUsuariosRoles;
    private DualListModel<SRol> rolDualListModel;
    private List<SRol> rolSource = new ArrayList<>();
    private List<SRol> rolTarget = new ArrayList<>();

    public void saveListRolesUsuarios() {
        JSONObject paramUpdate = new JSONObject();
        JSONObject paramDelete = new JSONObject();
        if (selectedUsuariosRoles != null) {

            JSONObject obj = new JSONObject();
            if (rolDualListModel.getTarget().size() == 1) {
                paramDelete.put("usr", selectedUsuariosRoles.getUsr());
                paramDelete.put("id_empresa", getId_empresa());
                String url = getHttpSegment() + "/WsRestFullJap/jap/srolusuario/dsrolusuarioall";
                String result = ClientRestFullResponse(url, paramDelete.toString()).getEntity(String.class);
                for (SRol up : rolDualListModel.getTarget()) {

                    paramUpdate.put("usr", selectedUsuariosRoles.getUsr());
                    paramUpdate.put("id_rol", up.getId());
                    paramUpdate.put("id_empresa", getId_empresa());
                    String urlUpdate = getHttpSegment() + "/WsRestFullJap/jap/srolusuario/ssopcionrolusuario";
                    String resultUpdate = ClientRestFullResponse(urlUpdate, paramUpdate.toString()).getEntity(String.class);
                    obj = new JSONObject(resultUpdate);

                }
                addsimplemessages((obj.length() != 0 ? obj.get("dato").toString() : "Opciones Registradas con Exito"));
                clearRolUsuario();
            } else {
                addsimplemessageswarn("El usuario solo puede tener un ROL Asignado");
                clearRolUsuario();
            }
        } else {
            addsimplemessageswarn("Debe de seleccionar un Usuario");
        }

    }

    public void clearRolUsuario() {
        selectedUsuariosRoles = new SUsuario();
        rolSource = new ArrayList<>();
        rolTarget = new ArrayList<>();
        rolDualListModel = new DualListModel<>(rolSource, rolTarget);

    }

    public void selectedUser() {
        loadDualListUserRolsource();
        loadDualListUserRoltaget();
    }

    private void loadtableUser() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/susuario/fsusuario";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        tableUsuarios = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SUsuario c = new SUsuario();
            c.setUsr(arr.getJSONObject(i).get("usr").toString());
            c.setClave(arr.getJSONObject(i).get("clave").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setEmail(arr.getJSONObject(i).get("email").toString());
            c.setNombre(arr.getJSONObject(i).get("nombre").toString());
            tableUsuarios.add(c);

        }

    }

    //las que no tiene
    public void loadDualListUserRolsource() {
        JSONObject param = new JSONObject();
        param.put("usr", selectedUsuariosRoles.getUsr());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/rolesusuariosn";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        rolSource = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SRol c = new SRol();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            rolSource.add(c);

        }
        rolDualListModel = new DualListModel<>(rolSource, rolTarget);

    }

    private String userjap;
    private String passjap;
    private List<MenuPrincipal> menuprincipal = new ArrayList<>();
    private List<SOpcionMenu> opcionesmenu = new ArrayList<>();

    public List<SOpcionMenu> getOpcionesmenu(String id_role, String id_opc_principal) {
        JSONObject paramOpc = new JSONObject();
        paramOpc.put("id_role", id_role);
        paramOpc.put("id_opc_principal", id_opc_principal);
        paramOpc.put("id_empresa", getId_empresa());
        String urlOPc = getHttpSegment() + "/WsRestFullJap/jap/sopcion/opcionesmenu";
        String resultOPc = ClientRestFullResponse(urlOPc, paramOpc.toString()).getEntity(String.class);
        JSONObject objOpc = new JSONObject(resultOPc);
        JSONArray arrOpc = objOpc.getJSONArray("dato");
        opcionesmenu = new ArrayList<>();
        for (int ii = 0; ii < arrOpc.length(); ii++) {
            SOpcionMenu cOp = new SOpcionMenu();
            CommandLink linkopciones = new CommandLink();
            cOp.setId(arrOpc.getJSONObject(ii).get("id").toString());
            cOp.setDescripcion(arrOpc.getJSONObject(ii).get("descripcion").toString());
            cOp.setStatus(arrOpc.getJSONObject(ii).get("status").toString());
            cOp.setProps_update(arrOpc.getJSONObject(ii).get("propsUpdate").toString());
            cOp.setProps_onclick(arrOpc.getJSONObject(ii).get("propsOnclick").toString());
            cOp.setProps_actionlistener(arrOpc.getJSONObject(ii).get("propsActionlistener").toString());

            cOp.setCommandlinkopciones(linkopciones);
            opcionesmenu.add(cOp);

        }

        return opcionesmenu;
    }

    public void userInvalidSpringMessage() {
        System.out.println("Se ejecuto");
        addsimplemessageserror("Usuario o contraseña invalidos");
    }

    public String loginJap() {
        try {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
            dispatcher.forward(request, response);

        } catch (Exception e) {

        }
        return null;
    }

    public String getUserjap() {
        return userjap;
    }

    public void setUserjap(String userjap) {
        this.userjap = userjap;
    }

    public List<MenuPrincipal> getMenuprincipal() {
        return menuprincipal;
    }

    public void setMenuprincipal(List<MenuPrincipal> menuprincipal) {
        this.menuprincipal = menuprincipal;
    }

    public String getPassjap() {
        return passjap;
    }

    public void setPassjap(String passjap) {
        this.passjap = passjap;
    }

    // las que ya tiene
    public void loadDualListUserRoltaget() {
        JSONObject param = new JSONObject();
        param.put("usr", selectedUsuariosRoles.getUsr());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/srol/rolesusuariosy";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        rolTarget = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SRol c = new SRol();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            rolTarget.add(c);

        }
        rolDualListModel = new DualListModel<>(rolSource, rolTarget);

    }

    public void loadSrolusuarioLink() {
        clearRolUsuario();
        loadtableUser();

    }
    /*...........::::::::::::::::::::::::. Metodos y Variables para Seguridad Opcion Principal :::::::::::::......................*/

    private boolean SopcionPrincipalEdit = true;
    private String SopcionPrincipalId;
    private String SopcionPrincipalDescripcion;
    private String SopcionPrincipalStatus;
    private SOpcionPrincipal SelectedOpcionPrincipalDTO;
    private List<SOpcionPrincipal> SopcionPrincpalList;
    private String pageOpcionPrincipal = "";

    private void loadSOpcionPrincpal() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionprincipal/fsopcionprincipal";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        SopcionPrincpalList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            SOpcionPrincipal c = new SOpcionPrincipal();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setMenu_icon(arr.getJSONObject(i).get("menuIcon").toString());
            c.setId_empresa(arr.getJSONObject(i).get("id_empresa").toString());
            SopcionPrincpalList.add(c);

        }

    }

    private void nextIdopcionPrincipal() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionprincipal/nextid";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setSopcionPrincipalId(obj.get("dato").toString());

    }

    public void ClearOpcionPrincipal() {
        setSopcionPrincipalDescripcion("");
        setSopcionPrincipalId("");
        setSopcionPrincipalStatus("0");
        setSopcionMenuIcon("");
        SelectedOpcionPrincipalDTO = new SOpcionPrincipal();
        nextIdopcionPrincipal();
        SopcionPrincipalEdit = true;
    }

    public void selectedRowOpcionPrincipal() {
        setSopcionPrincipalDescripcion(SelectedOpcionPrincipalDTO.getDescripcion());
        setSopcionPrincipalId(SelectedOpcionPrincipalDTO.getId());
        setSopcionPrincipalStatus(SelectedOpcionPrincipalDTO.getStatus());
        setSopcionMenuIcon(SelectedOpcionPrincipalDTO.getMenu_icon());
        SopcionPrincipalEdit = false;
    }

    public void cancelaropcionPrincipal() {
        ClearOpcionPrincipal();
    }

    public void loadSopcionPrincipalLink() {
        loadSOpcionPrincpal();
        nextIdopcionPrincipal();
        ClearOpcionPrincipal();
    }

    public void DSopcionPrincipal() {
        JSONObject param = new JSONObject();
        param.put("id", getSopcionPrincipalId());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionprincipal/dsopcionprincipal";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadSOpcionPrincpal();
        ClearOpcionPrincipal();

    }

    public void SSopcionPrincipal() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getSopcionPrincipalDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getSopcionPrincipalStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getSopcionPrincipalId());
            param.put("descripcion", getSopcionPrincipalDescripcion());
            param.put("status", getSopcionPrincipalStatus());
            param.put("menu_icon", getSopcionMenuIcon());
            param.put("id_empresa", getId_empresa());
            String url = getHttpSegment() + "/WsRestFullJap/jap/sopcionprincipal/ssopcionprincipal";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadSOpcionPrincpal();
            ClearOpcionPrincipal();
        }

    }

    public boolean isSopcionEdit() {
        return SopcionEdit;
    }

    public boolean isSopcionPrincipalEdit() {
        return SopcionPrincipalEdit;
    }

    public void setSopcionPrincipalEdit(boolean SopcionPrincipalEdit) {
        this.SopcionPrincipalEdit = SopcionPrincipalEdit;
    }

    public String getSopcionPrincipalId() {
        return SopcionPrincipalId;
    }

    public void setSopcionPrincipalId(String SopcionPrincipalId) {
        this.SopcionPrincipalId = SopcionPrincipalId;
    }

    public String getSopcionPrincipalDescripcion() {
        return SopcionPrincipalDescripcion;
    }

    public void setSopcionPrincipalDescripcion(String SopcionPrincipalDescripcion) {
        this.SopcionPrincipalDescripcion = SopcionPrincipalDescripcion;
    }

    public String getSopcionPrincipalStatus() {
        return SopcionPrincipalStatus;
    }

    public void setSopcionPrincipalStatus(String SopcionPrincipalStatus) {
        this.SopcionPrincipalStatus = SopcionPrincipalStatus;
    }

    public SOpcionPrincipal getSelectedOpcionPrincipalDTO() {
        return SelectedOpcionPrincipalDTO;
    }

    public void setSelectedOpcionPrincipalDTO(SOpcionPrincipal SelectedOpcionPrincipalDTO) {
        this.SelectedOpcionPrincipalDTO = SelectedOpcionPrincipalDTO;
    }

    public String getSopcionMenuIcon() {
        return SopcionMenuIcon;
    }

    public void setSopcionMenuIcon(String SopcionMenuIcon) {
        this.SopcionMenuIcon = SopcionMenuIcon;
    }

    public List<SOpcionPrincipal> getSopcionPrincpalList() {
        return SopcionPrincpalList;
    }

    public void setSopcionPrincpalList(List<SOpcionPrincipal> SopcionPrincpalList) {
        this.SopcionPrincpalList = SopcionPrincpalList;
    }

    public String getPageOpcionPrincipal() {
        return pageOpcionPrincipal;
    }

    public void setPageOpcionPrincipal(String pageOpcionPrincipal) {
        this.pageOpcionPrincipal = pageOpcionPrincipal;
    }

    public boolean isSrolEdit() {
        return SrolEdit;
    }

    public void setSrolEdit(boolean SrolEdit) {
        this.SrolEdit = SrolEdit;
    }

    public String getSrolId() {
        return SrolId;
    }

    public boolean isSusuarioEdit() {
        return SusuarioEdit;
    }

    public void setSusuarioEdit(boolean SusuarioEdit) {
        this.SusuarioEdit = SusuarioEdit;
    }

    public String getSusuarioUsr() {
        return SusuarioUsr;
    }

    public void setSusuarioUsr(String SusuarioUsr) {
        this.SusuarioUsr = SusuarioUsr;
    }

    public String getSusuarioNombre() {
        return SusuarioNombre;
    }

    public void setSusuarioNombre(String SusuarioNombre) {
        this.SusuarioNombre = SusuarioNombre;
    }

    public String getSusuarioStatus() {
        return SusuarioStatus;
    }

    public void setSusuarioStatus(String SusuarioStatus) {
        this.SusuarioStatus = SusuarioStatus;
    }

    public String getSusuarioClave() {
        return SusuarioClave;
    }

    public boolean isUsr_new_update() {
        return usr_new_update;
    }

    public void setUsr_new_update(boolean usr_new_update) {
        this.usr_new_update = usr_new_update;
    }

    public void setSusuarioClave(String SusuarioClave) {
        this.SusuarioClave = SusuarioClave;
    }

    public String getSusuarioEmail() {
        return SusuarioEmail;
    }

    public void setSusuarioEmail(String SusuarioEmail) {
        this.SusuarioEmail = SusuarioEmail;
    }

    public SUsuario getSelectedSUsuarioDTO() {
        return SelectedSUsuarioDTO;
    }

    public void setSelectedSUsuarioDTO(SUsuario SelectedSUsuarioDTO) {
        this.SelectedSUsuarioDTO = SelectedSUsuarioDTO;
    }

    public List<SUsuario> getSusuarioList() {
        return SusuarioList;
    }

    public void setSusuarioList(List<SUsuario> SusuarioList) {
        this.SusuarioList = SusuarioList;
    }

    public String getPageSusuario() {
        return pageSusuario;
    }

    public void setPageSusuario(String pageSusuario) {
        this.pageSusuario = pageSusuario;
    }

    public void setSrolId(String SrolId) {
        this.SrolId = SrolId;
    }

    public String getSrolDescripcion() {
        return SrolDescripcion;
    }

    public String getOpcionrolidRol() {
        return opcionrolidRol;
    }

    public void setOpcionrolidRol(String opcionrolidRol) {
        this.opcionrolidRol = opcionrolidRol;
    }

    public void setSrolDescripcion(String SrolDescripcion) {
        this.SrolDescripcion = SrolDescripcion;
    }

    public String getSrolStatus() {
        return SrolStatus;
    }

    public boolean isSopcionrolEdit() {
        return SopcionrolEdit;
    }

    public void setSopcionrolEdit(boolean SopcionrolEdit) {
        this.SopcionrolEdit = SopcionrolEdit;
    }

    public DualListModel<SOpcion> getOpcionDualListModel() {
        return opcionDualListModel;
    }

    public void setOpcionDualListModel(DualListModel<SOpcion> opcionDualListModel) {
        this.opcionDualListModel = opcionDualListModel;
    }

    public String getPageSopcionrol() {
        return pageSopcionrol;
    }

    public List<Cempresa> getCempresaList() {
        return CempresaList;
    }

    public void setCempresaList(List<Cempresa> CempresaList) {
        this.CempresaList = CempresaList;
    }

    public String getSelectedIdEmpresa() {
        return selectedIdEmpresa;
    }

    public void setSelectedIdEmpresa(String selectedIdEmpresa) {
        this.selectedIdEmpresa = selectedIdEmpresa;
    }

    public void setPageSopcionrol(String pageSopcionrol) {
        this.pageSopcionrol = pageSopcionrol;
    }

    public List<SRol> getCbxRolList() {
        return cbxRolList;
    }

    public void setCbxRolList(List<SRol> cbxRolList) {
        this.cbxRolList = cbxRolList;
    }

    public void setSrolStatus(String SrolStatus) {
        this.SrolStatus = SrolStatus;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    public SRol getSelectedSRolDTO() {
        return SelectedSRolDTO;
    }

    public List<SOpcion> getOpcionSource() {
        return opcionSource;
    }

    public void setOpcionSource(List<SOpcion> opcionSource) {
        this.opcionSource = opcionSource;
    }

    public List<SOpcion> getOpcionTarget() {
        return opcionTarget;
    }

    public void setOpcionTarget(List<SOpcion> opcionTarget) {
        this.opcionTarget = opcionTarget;
    }

    public void setSelectedSRolDTO(SRol SelectedSRolDTO) {
        this.SelectedSRolDTO = SelectedSRolDTO;
    }

    public List<SRol> getSrolList() {
        return SrolList;
    }

    public void setSrolList(List<SRol> SrolList) {
        this.SrolList = SrolList;
    }

    public void setSopcionEdit(boolean SopcionEdit) {
        this.SopcionEdit = SopcionEdit;
    }

    public String getSopcionId() {
        return SopcionId;
    }

    public void setSopcionId(String SopcionId) {
        this.SopcionId = SopcionId;
    }

    public String getSopcionDescripcion() {
        return SopcionDescripcion;
    }

    public void setSopcionDescripcion(String SopcionDescripcion) {
        this.SopcionDescripcion = SopcionDescripcion;
    }

    public String getSopcionStatus() {
        return SopcionStatus;
    }

    public void setSopcionStatus(String SopcionStatus) {
        this.SopcionStatus = SopcionStatus;
    }

    public SOpcion getSelectedSOpcionDTO() {
        return SelectedSOpcionDTO;
    }

    public void setSelectedSOpcionDTO(SOpcion SelectedSOpcionDTO) {
        this.SelectedSOpcionDTO = SelectedSOpcionDTO;
    }

    public List<SOpcion> getSopcionList() {
        return SopcionList;
    }

    public boolean isSrolusuarioEdit() {
        return SrolusuarioEdit;
    }

    public void setSrolusuarioEdit(boolean SrolusuarioEdit) {
        this.SrolusuarioEdit = SrolusuarioEdit;
    }

    public String getPageSrolusuario() {
        return pageSrolusuario;
    }

    public void setPageSrolusuario(String pageSrolusuario) {
        this.pageSrolusuario = pageSrolusuario;
    }

    public List<SUsuario> getTableUsuarios() {
        return tableUsuarios;
    }

    public void setTableUsuarios(List<SUsuario> tableUsuarios) {
        this.tableUsuarios = tableUsuarios;
    }

    public SUsuario getSelectedUsuariosRoles() {
        return selectedUsuariosRoles;
    }

    public void setSelectedUsuariosRoles(SUsuario selectedUsuariosRoles) {
        this.selectedUsuariosRoles = selectedUsuariosRoles;
    }

    public DualListModel<SRol> getRolDualListModel() {
        return rolDualListModel;
    }

    public void setRolDualListModel(DualListModel<SRol> rolDualListModel) {
        this.rolDualListModel = rolDualListModel;
    }

    public List<Cempleado> getCtipoEmpleadoList() {
        return CtipoEmpleadoList;
    }

    public void setCtipoEmpleadoList(List<Cempleado> CtipoEmpleadoList) {
        this.CtipoEmpleadoList = CtipoEmpleadoList;
    }

    public List<SRol> getRolSource() {
        return rolSource;
    }

    public void setRolSource(List<SRol> rolSource) {
        this.rolSource = rolSource;
    }

    public List<SRol> getRolTarget() {
        return rolTarget;
    }

    public void setRolTarget(List<SRol> rolTarget) {
        this.rolTarget = rolTarget;
    }

    public void setSopcionList(List<SOpcion> SopcionList) {
        this.SopcionList = SopcionList;
    }

    public String getPageSopcion() {
        return pageSopcion;
    }

    public void setPageSopcion(String pageSopcion) {
        this.pageSopcion = pageSopcion;
    }

    public String getPageSrol() {
        return pageSrol;
    }

    public void setPageSrol(String pageSrol) {
        this.pageSrol = pageSrol;
    }

    public CommandLink getLink_p() {
        return link_p;
    }

    public void setLink_p(CommandLink link_p) {
        this.link_p = link_p;
    }

    public String getId_oprcion_principal() {
        return id_oprcion_principal;
    }

    public void setId_oprcion_principal(String id_oprcion_principal) {
        this.id_oprcion_principal = id_oprcion_principal;
    }

    public List<SOpcionPrincipal> getCbxOpcionPrincipalList() {
        return cbxOpcionPrincipalList;
    }

    public void setCbxOpcionPrincipalList(List<SOpcionPrincipal> cbxOpcionPrincipalList) {
        this.cbxOpcionPrincipalList = cbxOpcionPrincipalList;
    }

    public boolean isHttp_sec_acces() {
        return http_sec_acces;
    }

    public String getSusuarioIdempleado() {
        return SusuarioIdempleado;
    }

    public void setSusuarioIdempleado(String SusuarioIdempleado) {
        this.SusuarioIdempleado = SusuarioIdempleado;
    }

    public void setHttp_sec_acces(boolean http_sec_acces) {
        this.http_sec_acces = http_sec_acces;
    }

}
