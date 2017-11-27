/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.sec;

import com.jap.client.dto.Cempleado;
import com.jap.client.dto.MenuPrincipal;
import com.jap.client.dto.SUsuario;
import com.jap.client.restfull.RestClientJapSeguridad;
import com.jap.client.utils.ClientUtils;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author irvin_monterroza
 */
public class AuthenticationProviderJap extends ClientUtils implements AuthenticationProvider {

    public AuthenticationProviderJap() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RestClientJapSeguridad sec = (RestClientJapSeguridad) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "restClientJapSeguridad");
        Authentication authRet = null;
        List<GrantedAuthority> roles = new ArrayList<>();
        GrantedAuthority role = null;
        String usuario = auth.getPrincipal().toString().trim();

        String password = auth.getCredentials().toString().trim();
        if (usuario.isEmpty() && password.isEmpty()) {
            sec.setLogueado(false);
            throw new BadCredentialsException("Usuario o Contraseña Invalidos");

        } else {
            System.out.println("ID EMPRESA:=" + sec.getSelectedIdEmpresa());
            setId_empresa(Integer.parseInt(sec.getSelectedIdEmpresa()));
            JSONObject paramlogin = new JSONObject();
            paramlogin.put("usr", usuario);
            paramlogin.put("clave", password);
            paramlogin.put("id_empresa", getId_empresa());
            String urllogin = getHttpSegment() + "/WsRestFullJap/jap/susuario/fsusuariologin";
            String resultogin = ClientRestFullResponse(urllogin, paramlogin.toString()).getEntity(String.class);
            JSONObject objlogin = new JSONObject(resultogin);
            JSONObject arrlogin = objlogin.getJSONObject("dato");
            if (sec.getUserjap().equalsIgnoreCase(arrlogin.get("usr").toString()) && sec.getPassjap().equalsIgnoreCase(arrlogin.get("clave").toString())) {
                JSONObject param = new JSONObject();
                param.put("usr", sec.getUserjap());
                param.put("id_empresa", sec.getSelectedIdEmpresa());
                String url = getHttpSegment() + "/WsRestFullJap/jap/susuario/fuserrolopcionesall";
                String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("dato");
                for (int i = 0; i < arr.length(); i++) {

                    MenuPrincipal c = new MenuPrincipal();
                    c.setId(arr.getJSONObject(i).get("idmenu").toString());
                    c.setDescripcion(arr.getJSONObject(i).get("descripcionmenuprincipal").toString());
                    c.setId_rol(arr.getJSONObject(i).get("id_rol").toString());
                    c.setMenu_icon(arr.getJSONObject(i).get("menu_icon").toString());
                    sec.getMenuprincipal().add(c);

                }
                SUsuario usu = fillUsuario(arrlogin);
                Cempleado empleado = loadEmpleado(usu);
                setUsuario(usu);
                setEmpleado(empleado);
                role = new SimpleGrantedAuthority("ROLE_AUTHENTICATION");
                roles.add(role);
                sec.setHttp_sec_acces(true);
                authRet = new UsernamePasswordAuthenticationToken(usuario, password, roles);
                sec.setLogueado(true);
            } else {
                sec.setLogueado(false);
                throw new BadCredentialsException("Usuario o Contraseña Invalidos");
            }
        }
        return authRet;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }   

    public SUsuario fillUsuario(JSONObject arrlogin) {
        SUsuario usu = new SUsuario();
        usu.setUsr(arrlogin.getString("usr"));
        usu.setClave(arrlogin.getString("clave"));
        usu.setStatus(arrlogin.getString("status"));
        usu.setEmail(arrlogin.getString("email"));
        usu.setNombre(arrlogin.getString("nombre"));
        usu.setEmpresa(arrlogin.get("empresa").toString());
        usu.setEmpleado(arrlogin.get("empleado").toString());
        return usu;
    }
    
    public Cempleado loadEmpleado(SUsuario usu) {
        JSONObject paramEmpSuc = new JSONObject();
        Cempleado empleado = new Cempleado();
        paramEmpSuc.put("idUsuario", usu.getUsr());
        paramEmpSuc.put("id", usu.getEmpleado());
        paramEmpSuc.put("idEmpresa", usu.getEmpresa());
        String urlEmpSuc = getHttpSegment() + "/WsRestFullJap/jap/cempleado/fcempleadosuc";
        String resultEmpSuc = ClientRestFullResponse(urlEmpSuc, paramEmpSuc.toString()).getEntity(String.class);
        JSONObject objEmpSuc = new JSONObject(resultEmpSuc);
        JSONObject arrEmpSuc = objEmpSuc.getJSONObject("dato");
        empleado.setNombre(arrEmpSuc.getString("nombre"));
        empleado.setIdSucursal(arrEmpSuc.getString("sucursal"));
        empleado.setIdEmpresa(arrEmpSuc.getString("idEmpr"));
        empleado.setId(usu.getEmpleado());
        empleado.setSucursal(arrEmpSuc.getString("idSucursal"));
        return empleado;
    }
}
