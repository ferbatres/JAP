/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.restfull;

import com.jap.client.dto.Calmacen;
import com.jap.client.dto.Ccategoria;
import com.jap.client.dto.Cciudad;
import com.jap.client.dto.Ccliente;
import com.jap.client.dto.Cdepto;
import com.jap.client.dto.Cempleado;
import com.jap.client.dto.Cempresa;
import com.jap.client.dto.Cestilo;
import com.jap.client.dto.Cfpago;
import com.jap.client.dto.Cgiro;
import com.jap.client.dto.Cmarca;
import com.jap.client.dto.Cmodelo;
import com.jap.client.dto.Cmunicipio;
import com.jap.client.dto.Cproducto;
import com.jap.client.dto.Cproducto_fnd;
import com.jap.client.dto.Cproveedor;
import com.jap.client.dto.Ctaller;
import com.jap.client.dto.CtipoDocumento;
import com.jap.client.dto.CtipoDp;
import com.jap.client.dto.CtipoProducto;
import com.jap.client.dto.CtipoVehiculo;
import com.jap.client.utils.ClientUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author irvin_monterroza
 */
@ManagedBean
@SessionScoped
public class RestClientJap extends ClientUtils implements Serializable {

    private String pageCTipoVehiculo = "";
    private String pageDetalleArticulos = "";
    /**
     * Creates a new instance of RestClientJap
     */
    /*...........:::::::::::::::::::::::: Metodos y Variables para Catalogo de Tipo de Vehiculos :::::::::::::......................*/
    /* Catalog de tipo de vehiculo variables*/
    private boolean CvehiculoEdit = true;
    private String CvehiculoId;
    private String CvehiculoDescripcion;
    private String CvehiculoStatus;
    private CtipoVehiculo SelectedVehiculoDTO;
    private List<CtipoVehiculo> CtipoVehiculoList;
    private String empleadoCheck;
    private String sucursalCheck;
    private String pageCTipoClienteSearch;
    private String pagecfiscalCTipoClienteSearch;
    private String pagecfiscalProductoSearch;    
    private String pageconsufiCTipoClienteSearch;
    private String pagedevoconsufiCTipoClienteSearch;
    private String pageProductoSearch;
    private String pageconsufiProductoSearch;
    private String pagedevoconsufiProductoSearch;
    private String pagenotaCTipoClienteSearch;
    private String pagenotaProductoSearch;
    private String pageNotaCreditoFac;
    private String pageFacordenEntrega;
    private String pagefacordenCTipoClienteSearch;
    private String pagefacordenProductoSearch;
    

    private void loadCtipoVehiculo() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipovehiculo/fctipovehiculo";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoVehiculoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            CtipoVehiculo c = new CtipoVehiculo();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoVehiculoList.add(c);

        }

    }

    private void nextIdCat() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipovehiculo/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCvehiculoId(obj.get("dato").toString());

    }
    private String httpParamHost = "";

    public RestClientJap() {
        pageCTipoVehiculo = "/catalogos/c_tipo_vehiculo.xhtml";
        pageCTipoDocumento = "/catalogos/c_tipo_documentos.xhtml";
        pageCTipoProd = "/catalogos/c_tipo_productos.xhtml";
        pageCTipoDp = "/catalogos/c_tipo_doc_proveedor.xhtml";
        pageCTipoMarca = "/catalogos/c_marca.xhtml";
        pageCTipoTaller = "/catalogos/c_taller.xhtml";
        pageCTipoEmpleado = "/catalogos/c_empleado.xhtml";
        pageCTipoEstilo = "/catalogos/c_estilo.xhtml";
        pageCTipoFpago = "/catalogos/c_f_pago.xhtml";
        pageCTipoModelo = "/catalogos/c_modelo.xhtml";
        pageCTipoAlmacen = "/catalogos/c_almacen.xhtml";
        pageCTipoGiro = "/catalogos/c_giro.xhtml";
        pageCTipoDepto = "/catalogos/c_depto.xhtml";
        pageCTipoMunicipio = "/catalogos/c_municipio.xhtml";
        pageCTipoCiudad = "/catalogos/c_ciudad.xhtml";
        pageCTipoCliente = "/catalogos/c_cliente.xhtml";
        pageProducto = "/catalogos/c_producto.xhtml";
        pageCTipoCategoria = "/catalogos/c_categoria.xhtml";
        pageCTipoProveedor = "/catalogos/c_proveedor.xhtml";
        pageDetalleArticulos = "/inventario_principal/detalle_info.xhtml";
        pageCTipoCempresa = "/catalogos/c_empresa.xhtml";

        pageProducto_fnd = "/inventario_principal/c_fnd_producto.xhtml";

        //Definicion de paginas para facturacion
        pageOrdenEntrega = "/facturacion/p_orden_entrega.xhtml";
        pageBuscarDoc = "/facturacion/f_orden_entrega.xhtml";
        pageCTipoClienteSearch = "/facturacion/find_cliente.xhtml";
        pageconsufiCTipoClienteSearch="/facturacion/find_cliente_consumidor.xhtml";
        pagedevoconsufiCTipoClienteSearch="/facturacion/find_cliente_devoconsumidor.xhtml";
        pageProductoSearch = "/facturacion/find_producto.xhtml";
        pageconsufiProductoSearch = "/facturacion/find_producto_consumidor.xhtml";
        pagedevoconsufiProductoSearch = "/facturacion/find_producto_devoconsumidor.xhtml";
        pagenotaCTipoClienteSearch = "/facturacion/find_cliente_nota.xhtml";
        pagenotaProductoSearch = "/facturacion/find_producto_nota.xhtml";        
        pageConsumidorFinal = "/facturacion/f_consumidor_final.xhtml";
        pagedevoConsumidorFinal = "/facturacion/f_consumidor_devofinal.xhtml";
        pageCreditoFiscal = "/facturacion/f_credito_fiscal.xhtml";
        pageNotaCreditoFac ="/facturacion/f_nota_credito.xhtml";
        pageFacordenEntrega ="/facturacion/f_facturar_orden.xhtml";
        pagecfiscalCTipoClienteSearch = "/facturacion/find_cliente_cfiscal.xhtml";
        pagecfiscalProductoSearch="/facturacion/find_producto_cfiscal.xhtml";
        
        pagefacordenCTipoClienteSearch = "/facturacion/find_cliente_forden.xhtml";
        pagefacordenProductoSearch="/facturacion/find_producto_forden.xhtml";
    }

    public void ClearVehiculos() {
        setCvehiculoDescripcion("");
        setCvehiculoId("");
        setCvehiculoStatus("0");
        SelectedVehiculoDTO = new CtipoVehiculo();
        nextIdCat();
        CvehiculoEdit = true;
    }

    public void selectedRowVehiculo() {
        setCvehiculoDescripcion(SelectedVehiculoDTO.getDescripcion());
        setCvehiculoId(SelectedVehiculoDTO.getId());
        setCvehiculoStatus(SelectedVehiculoDTO.getStatus());
        CvehiculoEdit = false;
    }

    public void cancelarVehiculos() {
        ClearVehiculos();
    }

    public void loadCtipoVehiculoLink() {
        loadCtipoVehiculo();
        nextIdCat();
        ClearVehiculos();
    }

    public void DCtipoVehiculo() {
        JSONObject param = new JSONObject();
        param.put("id", getCvehiculoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipovehiculo/dctipovehiculo";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoVehiculo();
        ClearVehiculos();

    }

    public void SCtipoVehiculo() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCvehiculoDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCvehiculoStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCvehiculoId());
            param.put("descripcion", getCvehiculoDescripcion());
            param.put("status", getCvehiculoStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ctipovehiculo/sctipovehiculo";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoVehiculo();
            ClearVehiculos();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo de Tipo de Documento:::::::::::::......................*/
    private boolean CdocumentoEdit = true;
    private String CdocumentoId;
    private String CdocumentoDescripcion;
    private String CdocumentoStatus;
    private CtipoDocumento SelectedDocumentoDTO;
    private List<CtipoDocumento> CtipoDocumentoList;
    private String pageCTipoDocumento = "";

    private void loadCtipoDocumento() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodoc/fctipodoc";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoDocumentoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            CtipoDocumento c = new CtipoDocumento();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoDocumentoList.add(c);

        }

    }

    private void nextIdDoc() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodoc/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCdocumentoId(obj.get("dato").toString());

    }

    public void ClearDocumento() {
        setCdocumentoDescripcion("");
        setCdocumentoId("");
        setCdocumentoStatus("0");
        SelectedDocumentoDTO = new CtipoDocumento();
        nextIdDoc();
        CdocumentoEdit = true;
    }

    public void selectedRowDocumento() {
        setCdocumentoDescripcion(SelectedDocumentoDTO.getDescripcion());
        setCdocumentoId(SelectedDocumentoDTO.getId());
        setCdocumentoStatus(SelectedDocumentoDTO.getStatus());
        CdocumentoEdit = false;
    }

    public void cancelarDocumentos() {
        ClearDocumento();
    }

    public void loadCtipoDocumentoLink() {
        loadCtipoDocumento();
        nextIdDoc();
        ClearDocumento();
    }

    public void DCtipoDocumento() {
        JSONObject param = new JSONObject();
        param.put("id", getCdocumentoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodoc/dctipodoc";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoDocumento();
        ClearDocumento();

    }

    public void SCtipoDocumento() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCdocumentoDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCdocumentoStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCdocumentoId());
            param.put("descripcion", getCdocumentoDescripcion());
            param.put("status", getCdocumentoStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodoc/sctipodoc";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoDocumento();
            ClearDocumento();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo de Tipo de Producto :::::::::::::......................*/
    private boolean CprodEdit = true;
    private String CprodId;
    private String CprodDescripcion;
    private String CprodStatus;
    private CtipoProducto SelectedProdDTO;
    private List<CtipoProducto> CtipoProdList;
    private String pageCTipoProd = "";

    private void loadCtipoProd() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/tipoproducto/fctipoproducto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoProdList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            CtipoProducto c = new CtipoProducto();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoProdList.add(c);

        }

    }

    private void nextIdProd() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/tipoproducto/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCprodId(obj.get("dato").toString());

    }

    public void ClearProd() {
        setCprodDescripcion("");
        setCprodId("");
        setCprodStatus("0");
        SelectedProdDTO = new CtipoProducto();
        nextIdProd();
        CprodEdit = true;
    }

    public void selectedRowProd() {
        setCprodDescripcion(SelectedProdDTO.getDescripcion());
        setCprodId(SelectedProdDTO.getId());
        setCprodStatus(SelectedProdDTO.getStatus());
        CprodEdit = false;
    }

    public void cancelarProd() {
        ClearProd();
    }

    public void loadCtipoProdLink() {
        loadCtipoProd();
        nextIdProd();
        ClearProd();
    }

    public void DCtipoProd() {
        JSONObject param = new JSONObject();
        param.put("id", getCprodId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/tipoproducto/dctipoproducto";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoProd();
        ClearProd();

    }

    public void SCtipoProd() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCprodDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCprodStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCprodId());
            param.put("descripcion", getCprodDescripcion());
            param.put("status", getCprodStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/tipoproducto/sctipoproducto";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoProd();
            ClearProd();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo de Tipo de Documento Proveedor :::::::::::::......................*/
    private boolean CdpEdit = true;
    private String CdpId;
    private String CdpDescripcion;
    private String CdpStatus;
    private CtipoDp SelectedDpDTO;
    private List<CtipoDp> CtipoDpList;
    private String pageCTipoDp = "";

    private void loadCtipoDp() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodocproveedor/fctipodocproveedor";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoDpList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            CtipoDp c = new CtipoDp();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoDpList.add(c);

        }

    }

    private void nextIdDp() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodocproveedor/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCdpId(obj.get("dato").toString());

    }

    public void ClearDp() {
        setCdpDescripcion("");
        setCdpId("");
        setCdpStatus("0");
        SelectedDpDTO = new CtipoDp();
        nextIdDp();
        CdpEdit = true;
    }

    public void selectedRowDp() {
        setCdpDescripcion(SelectedDpDTO.getDescripcion());
        setCdpId(SelectedDpDTO.getId());
        setCdpStatus(SelectedDpDTO.getStatus());
        CdpEdit = false;
    }

    public void cancelarDp() {
        ClearProd();
    }

    public void loadCtipoDpLink() {
        loadCtipoDp();
        nextIdDp();
        ClearDp();
    }

    public void DCtipoDp() {
        JSONObject param = new JSONObject();
        param.put("id", getCdpId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodocproveedor/dctipodocproveedor";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoDp();
        ClearDp();

    }

    public void SCtipoDp() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCdpDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCdpStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCdpId());
            param.put("descripcion", getCdpDescripcion());
            param.put("status", getCdpStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodocproveedor/sctipodocproveedor";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoDp();
            ClearDp();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Marca :::::::::::::......................*/
    private boolean CmarcaEdit = true;
    private String CmarcaId;
    private String CmarcaDescripcion;
    private String CmarcaStatus;
    private Cmarca SelectedMarcaDTO;
    private List<Cmarca> CtipoMarcaList;
    private String pageCTipoMarca = "";

    private void loadCtipoMarca() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/fcmarca";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoMarcaList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmarca c = new Cmarca();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoMarcaList.add(c);

        }

    }

    private void nextIdMarca() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCmarcaId(obj.get("dato").toString());

    }

    public void ClearMarca() {
        setCmarcaDescripcion("");
        setCmarcaId("");
        setCmarcaStatus("0");
        SelectedMarcaDTO = new Cmarca();
        nextIdMarca();
        CmarcaEdit = true;
    }

    public void selectedRowMarca() {
        setCmarcaDescripcion(SelectedMarcaDTO.getDescripcion());
        setCmarcaId(SelectedMarcaDTO.getId());
        setCmarcaStatus(SelectedMarcaDTO.getStatus());
        CmarcaEdit = false;
    }

    public void cancelarMarca() {
        ClearMarca();
    }

    public void loadCtipoMarcaLink() {
        loadCtipoMarca();
        nextIdMarca();
        ClearMarca();
    }

    public void DCtipoMarca() {
        JSONObject param = new JSONObject();
        param.put("id", getCmarcaId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/dcmarca";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoMarca();
        ClearMarca();

    }

    public void SCtipoMarca() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCmarcaDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCmarcaStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCmarcaId());
            param.put("descripcion", getCmarcaDescripcion());
            param.put("status", getCmarcaStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/scmarca";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoMarca();
            ClearMarca();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Taller :::::::::::::......................*/
    private boolean CtallerEdit = true;
    private String CtallerId;
    private String CtallerDescripcion;
    private String CtallerStatus;
    private Ctaller SelectedTallerDTO;
    private List<Ctaller> CtipoTallerList;
    private String pageCTipoTaller = "";

    private void loadCtipoTaller() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/taller/ftaller";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoTallerList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Ctaller c = new Ctaller();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoTallerList.add(c);

        }

    }

    private void nextIdTaller() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/taller/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCtallerId(obj.get("dato").toString());

    }

    public void ClearTaller() {
        setCtallerDescripcion("");
        setCtallerId("");
        setCtallerStatus("0");
        SelectedTallerDTO = new Ctaller();
        nextIdTaller();
        CtallerEdit = true;
    }

    public void selectedRowTaller() {
        setCtallerDescripcion(SelectedTallerDTO.getDescripcion());
        setCtallerId(SelectedTallerDTO.getId());
        setCtallerStatus(SelectedTallerDTO.getStatus());
        CtallerEdit = false;
    }

    public void cancelarTaller() {
        ClearMarca();
    }

    public void loadCtipoTallerLink() {
        loadCtipoTaller();
        nextIdTaller();
        ClearTaller();
    }

    public void DCtipoTaller() {
        JSONObject param = new JSONObject();
        param.put("id", getCtallerId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/taller/dtaller";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoTaller();
        ClearTaller();

    }

    public void SCtipoTaller() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCtallerDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCtallerStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCtallerId());
            param.put("descripcion", getCtallerDescripcion());
            param.put("status", getCtallerStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/taller/staller";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoTaller();
            ClearTaller();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Empleado :::::::::::::......................*/
    private boolean CempleadoEdit = true;
    private String CempleadoId;
    private String CempleadoDescripcion;
    private String CempleadoStatus;
    private Cempleado SelectedEmpleadoDTO;
    private List<Cempleado> CtipoEmpleadoList;
    private String pageCTipoEmpleado = "";

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

    private void nextIdEmpleado() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempleado/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCempleadoId(obj.get("dato").toString());
    }

    public void ClearEmpleado() {
        setCempleadoDescripcion("");
        setCempleadoId("");
        setCempleadoStatus("0");
        SelectedEmpleadoDTO = new Cempleado();
        nextIdEmpleado();
        CempleadoEdit = true;
    }

    public void selectedRowEmpleado() {
        setCempleadoDescripcion(SelectedEmpleadoDTO.getDescripcion());
        setCempleadoId(SelectedEmpleadoDTO.getId());
        setCempleadoStatus(SelectedEmpleadoDTO.getStatus());
        CempleadoEdit = false;
    }

    public void cancelarEmpleado() {
        ClearEmpleado();
    }

    public void loadCtipoEmpleadoLink() {
        loadCtipoEmpleado();
        nextIdEmpleado();
        ClearEmpleado();
    }

    public void DCtipoEmpleado() {
        JSONObject param = new JSONObject();
        param.put("id", getCempleadoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempleado/dcempleado";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoEmpleado();
        ClearEmpleado();

    }

    public void SCtipoEmpleado() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCempleadoDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCempleadoStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCempleadoId());
            param.put("nombre", getCempleadoDescripcion());
            param.put("status", getCempleadoStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cempleado/scempleado";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoEmpleado();
            ClearEmpleado();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Estilo :::::::::::::......................*/
    private boolean CestiloEdit = true;
    private String CestiloId;
    private String CestiloDescripcion;
    private String CestiloStatus;
    private Cestilo SelectedEstiloDTO;
    private List<Cestilo> CtipoEstiloList;
    private String pageCTipoEstilo = "";

    private void loadCtipoEstilo() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cestilo/fcestilo";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoEstiloList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cestilo c = new Cestilo();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoEstiloList.add(c);

        }

    }

    private void nextIdEstilo() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cestilo/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCestiloId(obj.get("dato").toString());

    }

    public void ClearEstilo() {
        setCestiloDescripcion("");
        setCestiloId("");
        setCestiloStatus("0");
        SelectedEstiloDTO = new Cestilo();
        nextIdEstilo();
        CestiloEdit = true;
    }

    public void selectedRowEstilo() {
        setCestiloDescripcion(SelectedEstiloDTO.getDescripcion());
        setCestiloId(SelectedEstiloDTO.getId());
        setCestiloStatus(SelectedEstiloDTO.getStatus());
        CestiloEdit = false;
    }

    public void cancelarEstilo() {
        ClearEstilo();
    }

    public void loadCtipoEstiloLink() {
        loadCtipoEstilo();
        nextIdEstilo();
        ClearEstilo();
    }

    public void DCtipoEstilo() {
        JSONObject param = new JSONObject();
        param.put("id", getCestiloId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cestilo/dcestilo";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoEstilo();
        ClearEstilo();

    }

    public void SCtipoEstilo() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCestiloDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCestiloStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCestiloId());
            param.put("descripcion", getCestiloDescripcion());
            param.put("status", getCestiloStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cestilo/scestilo";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoEstilo();
            ClearEstilo();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Empresa :::::::::::::......................*/
    private boolean CempresaEdit = true;
    private String CempresaId;
    private String CempresaDescripcion;
    private String CempresaStatus;
    private Cempresa SelectedCempresaDTO;
    private List<Cempresa> CtipoCempresaList;
    private String pageCTipoCempresa = "";
    private String pageOrdenEntrega = "";
    private String pageConsumidorFinal="";
    private String pagedevoConsumidorFinal="";
    private String pageCreditoFiscal="";
    private String pageBuscarDoc = "";

    private void loadCtipoCempresa() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempresa/fcempresa";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoCempresaList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cempresa c = new Cempresa();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoCempresaList.add(c);

        }

    }

    private void nextIdCempresa() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempresa/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCempresaId(obj.get("dato").toString());

    }

    public void ClearCempresa() {
        setCempresaDescripcion("");
        setCempresaId("");
        setCempresaStatus("0");
        SelectedCempresaDTO = new Cempresa();
        nextIdCempresa();
        CempresaEdit = true;
    }

    public void selectedRowCempresa() {
        setCempresaDescripcion(SelectedCempresaDTO.getDescripcion());
        setCempresaId(SelectedCempresaDTO.getId());
        setCempresaStatus(SelectedCempresaDTO.getStatus());
        CempresaEdit = false;
    }

    public void cancelarCempresa() {
        ClearCempresa();
    }

    public void loadCtipoCempresaLink() {
        loadCtipoCempresa();
        nextIdCempresa();
        ClearCempresa();
    }

    public void DCtipoCempresa() {
        JSONObject param = new JSONObject();
        param.put("id", getCempresaId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempresa/dcempresa";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoCempresa();
        ClearCempresa();

    }

    public void SCtipoCempresa() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCempresaDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCempresaStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCempresaId());
            param.put("descripcion", getCempresaDescripcion());
            param.put("status", getCempresaStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cempresa/scempresa";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoCempresa();
            ClearCempresa();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Forma de Pago :::::::::::::......................*/
    private boolean CfpagoEdit = true;
    private String CfpagoId;
    private String CfpagoDescripcion;
    private String CfpagoStatus;
    private Cfpago SelectedFpagoDTO;
    private List<Cfpago> CtipoFpagoList;
    private String pageCTipoFpago = "";

    private void loadCtipoFpago() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/fcformadepago";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoFpagoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cfpago c = new Cfpago();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoFpagoList.add(c);

        }

    }

    private void nextIdFpago() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCfpagoId(obj.get("dato").toString());

    }

    public void ClearFpago() {
        setCfpagoDescripcion("");
        setCfpagoId("");
        setCfpagoStatus("0");
        SelectedFpagoDTO = new Cfpago();
        nextIdFpago();
        CfpagoEdit = true;
    }

    public void selectedRowFpago() {
        setCfpagoDescripcion(SelectedFpagoDTO.getDescripcion());
        setCfpagoId(SelectedFpagoDTO.getId());
        setCfpagoStatus(SelectedFpagoDTO.getStatus());
        CfpagoEdit = false;
    }

    public void cancelarFpago() {
        ClearFpago();
    }

    public void loadCtipoFpagoLink() {
        loadCtipoFpago();
        nextIdFpago();
        ClearFpago();
    }

    public void DCtipoFpago() {
        JSONObject param = new JSONObject();
        param.put("id", getCfpagoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/dcformadepago";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoFpago();
        ClearFpago();

    }

    public void SCtipoFpago() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCfpagoDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCfpagoStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCfpagoId());
            param.put("descripcion", getCfpagoDescripcion());
            param.put("status", getCfpagoStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/scformadepago";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoFpago();
            ClearFpago();
        }

    }
    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Modelo :::::::::::::......................*/

    private boolean CmodeloEdit = true;
    private String CmodeloId;
    private String CmodeloDescripcion;
    private String CmodeloStatus;
    private String CmodeloDescripcionMarca;
    private String CmodeloIdMarca;
    private Cmodelo SelectedModeloDTO;
    private List<Cmodelo> CtipoModeloList;
    private List<Cmarca> cModeloMarcaListcbx;
    private List<Cmarca> cmodelomarcalist;
    private String pageCTipoModelo = "";
    private String selectedModeloMarcaCbx;

    private void loadcbxMarca() {

        cmodelomarcalist = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/fcmarca";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Cmarca c = new Cmarca();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            cmodelomarcalist.add(c);

        }

    }

    private void loadCtipoModelo() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/fcmodelo";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoModeloList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmodelo c = new Cmodelo();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setIdMarca(arr.getJSONObject(i).get("idmarca").toString());
            c.setDescripcionMarca(arr.getJSONObject(i).get("descripcionmarca").toString());
            CtipoModeloList.add(c);

        }
        loadcbxMarca();

    }

    private void nextIdModelo() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCmodeloId(obj.get("dato").toString());

    }

    public void ClearModelo() {
        setCmodeloDescripcion("");
        setCmodeloId("");
        setCmodeloStatus("0");
        setSelectedModeloMarcaCbx("");
        SelectedModeloDTO = new Cmodelo();
        nextIdModelo();
        CmodeloEdit = true;
    }

    public void selectedRowModelo() {
        setCmodeloDescripcion(SelectedModeloDTO.getDescripcion());
        setCmodeloId(SelectedModeloDTO.getId());
        setCmodeloStatus(SelectedModeloDTO.getStatus());
        setSelectedModeloMarcaCbx(SelectedModeloDTO.getIdMarca());
        CmodeloEdit = false;
    }

    public void cancelarModelo() {
        ClearModelo();
    }

    public void loadCtipoModeloLink() {
        loadCtipoModelo();
        nextIdModelo();
        ClearModelo();
    }

    public void DCtipoModelo() {
        JSONObject param = new JSONObject();
        param.put("id", getCmodeloId());
        param.put("idMarca", getSelectedModeloMarcaCbx());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/dcmodelo";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoModelo();
        ClearModelo();

    }

    public void SCtipoModelo() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCmodeloDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCmodeloStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (ValidNullString(getSelectedModeloMarcaCbx())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar Marca");
        }
        if (!validate) {
            param.put("id", getCmodeloId());
            param.put("descripcion", getCmodeloDescripcion());
            param.put("status", getCmodeloStatus());
            param.put("id_marca", getSelectedModeloMarcaCbx());

            String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/scmodelo";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoModelo();
            ClearModelo();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Almacen :::::::::::::......................*/
    private boolean CalmacenEdit = true;
    private String CalmacenId;
    private String CalmacenDescripcion;
    private String CalmacenStatus;
    private String CalmacenDireccion1;
    private String CalmacenDireccion2;
    private String CalmacenTel1;
    private String CalmacenTel2;
    private String CalmacenTransferible;

    private Calmacen SelectedAlmacenDTO;
    private List<Calmacen> CtipoAlmacenList;
    private String pageCTipoAlmacen = "";

    private void loadCtipoAlmacen() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/calmacen/fcalmacen";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoAlmacenList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Calmacen c = new Calmacen();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setDireccion1(arr.getJSONObject(i).get("direccion1").toString());
            c.setDireccion2(arr.getJSONObject(i).get("direccion2").toString());
            c.setTel1(arr.getJSONObject(i).get("tel1").toString());
            c.setTel2(arr.getJSONObject(i).get("tel2").toString());
            c.setTransferible(arr.getJSONObject(i).get("transferible").toString());
            c.setId_empresa(arr.getJSONObject(i).get("id_empresa").toString());
            CtipoAlmacenList.add(c);

        }

    }

    private void nextIdAlmacen() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/calmacen/nextid";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCalmacenId(obj.get("dato").toString());

    }

    public void ClearAlmacen() {
        setCalmacenDescripcion("");
        setCalmacenId("");
        setCalmacenStatus("0");
        setCalmacenDireccion1("");
        setCalmacenDireccion2("");
        setCalmacenTel1("");
        setCalmacenTel2("");
        setCalmacenTransferible("");
        SelectedAlmacenDTO = new Calmacen();
        nextIdAlmacen();
        CalmacenEdit = true;
    }

    public void selectedRowAlmacen() {
        setCalmacenDescripcion(SelectedAlmacenDTO.getDescripcion());
        setCalmacenId(SelectedAlmacenDTO.getId());
        setCalmacenStatus(SelectedAlmacenDTO.getStatus());
        setCalmacenDireccion1(SelectedAlmacenDTO.getDireccion1());
        setCalmacenDireccion2(SelectedAlmacenDTO.getDireccion2());
        setCalmacenTel1(SelectedAlmacenDTO.getTel1());
        setCalmacenTel2(SelectedAlmacenDTO.getTel2());
        setCalmacenTransferible(SelectedAlmacenDTO.getTransferible());

        CalmacenEdit = false;
    }

    public void cancelarAlmacen() {
        ClearAlmacen();
    }

    public void loadCtipoAlmacenLink() {
        loadCtipoAlmacen();
        nextIdAlmacen();
        ClearAlmacen();
    }

    public void DCtipoAlmacen() {
        JSONObject param = new JSONObject();
        param.put("id", getCalmacenId());
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/calmacen/dcalmacen";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoAlmacen();
        ClearAlmacen();

    }

    public void SCtipoAlmacen() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCalmacenDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCalmacenStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (ValidNullString(getCalmacenDireccion1()) && ValidNullString(getCalmacenDireccion2())) {
            validate = true;
            addsimplemessageserror("Debe de ingresar una direccion");
        }

        if (ValidNullString(getCalmacenTel1()) && ValidNullString(getCalmacenTel2())) {
            validate = true;
            addsimplemessageserror("Debe de ingresar un numero de telefono");
        }
        if (!validate) {
            param.put("id", getCalmacenId());
            param.put("descripcion", getCalmacenDescripcion());
            param.put("status", getCalmacenStatus());
            param.put("direccion1", getCalmacenDireccion1());
            param.put("direccion2", getCalmacenDireccion2());
            param.put("tel1", getCalmacenTel1());
            param.put("tel2", getCalmacenTel2());
            param.put("transferible", getCalmacenTransferible());
            param.put("id_empresa", getId_empresa());
            String url = getHttpSegment() + "/WsRestFullJap/jap/calmacen/scalmacen";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoAlmacen();
            ClearAlmacen();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Giro :::::::::::::......................*/
    private boolean CgiroEdit = true;
    private String CgiroId;
    private String CgiroDescripcion;
    private String CgiroStatus;
    private Cgiro SelectedGiroDTO;
    private List<Cgiro> CtipoGiroList;
    private String pageCTipoGiro = "";

    private void loadCtipoGiro() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cgiro/fcgiro";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoGiroList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cgiro c = new Cgiro();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoGiroList.add(c);

        }

    }

    private void nextIdGiro() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cgiro/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCgiroId(obj.get("dato").toString());

    }

    public void ClearGiro() {
        setCgiroDescripcion("");
        setCgiroId("");
        setCgiroStatus("0");
        SelectedGiroDTO = new Cgiro();
        nextIdGiro();
        CgiroEdit = true;
    }

    public void selectedRowGiro() {
        setCgiroDescripcion(SelectedGiroDTO.getDescripcion());
        setCgiroId(SelectedGiroDTO.getId());
        setCgiroStatus(SelectedGiroDTO.getStatus());
        CgiroEdit = false;
    }

    public void cancelarGiro() {
        ClearGiro();
    }

    public void loadCtipoGiroLink() {
        loadCtipoGiro();
        nextIdGiro();
        ClearGiro();
    }

    public void DCtipoGiro() {
        JSONObject param = new JSONObject();
        param.put("id", getCgiroId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cgiro/dcgiro";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoGiro();
        ClearGiro();

    }

    public void SCtipoGiro() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCgiroDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCgiroStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCgiroId());
            param.put("descripcion", getCgiroDescripcion());
            param.put("status", getCgiroStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cgiro/scgiro";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoGiro();
            ClearGiro();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Depto :::::::::::::......................*/
    private boolean CdeptoEdit = true;
    private String CdeptoId;
    private String CdeptoDescripcion;
    private String CdeptoStatus;
    private Cdepto SelectedDeptoDTO;
    private List<Cdepto> CtipoDeptoList;
    private String pageCTipoDepto = "";

    private void loadCtipoDepto() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/fcdepto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoDeptoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cdepto c = new Cdepto();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoDeptoList.add(c);

        }

    }

    private void nextIdDepto() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCdeptoId(obj.get("dato").toString());

    }

    public void ClearDepto() {
        setCdeptoDescripcion("");
        setCdeptoId("");
        setCdeptoStatus("0");
        SelectedDeptoDTO = new Cdepto();
        nextIdDepto();
        CdeptoEdit = true;
    }

    public void selectedRowDepto() {
        setCdeptoDescripcion(SelectedDeptoDTO.getDescripcion());
        setCdeptoId(SelectedDeptoDTO.getId());
        setCdeptoStatus(SelectedDeptoDTO.getStatus());
        CdeptoEdit = false;
    }

    public void cancelarDepto() {
        ClearDepto();
    }

    public void loadCtipoDeptoLink() {
        loadCtipoDepto();
        nextIdDepto();
        ClearDepto();
    }

    public void DCtipoDepto() {
        JSONObject param = new JSONObject();
        param.put("id", getCdeptoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/dcdepto";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoDepto();
        ClearDepto();

    }

    public void SCtipoDepto() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCdeptoDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCdeptoStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCdeptoId());
            param.put("descripcion", getCdeptoDescripcion());
            param.put("status", getCdeptoStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/scdepto";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoDepto();
            ClearDepto();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Municipio :::::::::::::......................*/
    private boolean CmunicipioEdit = true;
    private String CmunicipioId;
    private String CmunicipioDescripcion;
    private String CmunicipioStatus;
    private String CmunicipioDescripcionDepto;
    private String CmunicipioIdDepto;
    private Cmunicipio SelectedMunicipioDTO;
    private List<Cmunicipio> CtipoMunicipioList;
    private List<Cdepto> cMunicipioDeptoListcbx;
    private List<Cdepto> cmunicipiodeptolist;
    private String pageCTipoMunicipio = "";
    private String selectedMunicipioDeptoCbx;

    private void loadcbxDepto() {

        cmunicipiodeptolist = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/fcdepto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Cdepto c = new Cdepto();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            cmunicipiodeptolist.add(c);

        }

    }

    private void loadCtipoMunicipio() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/fcmunicipio";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoMunicipioList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmunicipio c = new Cmunicipio();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setIdDepto(arr.getJSONObject(i).get("iddepto").toString());
            c.setDescripcionDepto(arr.getJSONObject(i).get("descripciondepto").toString());
            CtipoMunicipioList.add(c);

        }
        loadcbxDepto();

    }

    private void nextIdMunicipio() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCmunicipioId(obj.get("dato").toString());

    }

    public void ClearMunicipio() {
        setCmunicipioDescripcion("");
        setCmunicipioId("");
        setCmunicipioStatus("0");
        setSelectedMunicipioDeptoCbx("");
        SelectedMunicipioDTO = new Cmunicipio();
        nextIdMunicipio();
        CmunicipioEdit = true;
    }

    public void selectedRowMunicipio() {
        setCmunicipioDescripcion(SelectedMunicipioDTO.getDescripcion());
        setCmunicipioId(SelectedMunicipioDTO.getId());
        setCmunicipioStatus(SelectedMunicipioDTO.getStatus());
        setSelectedMunicipioDeptoCbx(SelectedMunicipioDTO.getIdDepto());
        CmunicipioEdit = false;
    }

    public void cancelarMunicipio() {
        ClearMunicipio();
    }

    public void loadCtipoMunicipioLink() {
        loadCtipoMunicipio();
        nextIdMunicipio();
        ClearMunicipio();
    }

    public void DCtipoMunicipio() {
        JSONObject param = new JSONObject();
        param.put("id", getCmunicipioId());
        param.put("idDepto", getSelectedMunicipioDeptoCbx());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/dcmunicipio";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoMunicipio();
        ClearMunicipio();

    }

    public void SCtipoMunicipio() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCmunicipioDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCmunicipioStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (ValidNullString(getSelectedMunicipioDeptoCbx())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar Depto");
        }
        if (!validate) {
            param.put("id", getCmunicipioId());
            param.put("descripcion", getCmunicipioDescripcion());
            param.put("status", getCmunicipioStatus());
            param.put("id_depto", getSelectedMunicipioDeptoCbx());

            String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/scmunicipio";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoMunicipio();
            ClearMunicipio();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Ciudad :::::::::::::......................*/
    private boolean CciudadEdit = true;
    private String CciudadId;
    private String CciudadDescripcion;
    private String CciudadStatus;
    private String CciudadmunicipioId;
    private String CciudaddeptoId;
    private Cciudad SelectedCiudadDTO;
    private List<Cciudad> CtipoCiudadList;
    private List<Cdepto> selectedCiudadDeptoCbx;
    private List<Cmunicipio> selectedCiudadDeptoMunicipioCbx;
    private String pageCTipoCiudad = "";

    private void loadCtipoCiudad() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cciudad/fcciudad";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoCiudadList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cciudad c = new Cciudad();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setIdmunicipio(arr.getJSONObject(i).get("idmunicipio").toString());
            c.setIddepto(arr.getJSONObject(i).get("iddepto").toString());
            c.setDescripciondepto(arr.getJSONObject(i).get("descripciondepto").toString());
            c.setDescripcionmunicpio(arr.getJSONObject(i).get("descripcionmunicpio").toString());

            CtipoCiudadList.add(c);

        }

    }

    private void nextIdCiudad() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cciudad/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCciudadId(obj.get("dato").toString());

    }

    public void ClearCiudad() {
        setCciudadDescripcion("");
        setCciudadId("");
        setCciudadStatus("0");
        setCciudaddeptoId("");
        setCciudadmunicipioId("");
        SelectedCiudadDTO = new Cciudad();
        nextIdCiudad();
        CciudadEdit = true;
    }

    public void selectedRowCiudad() {
        setCciudadDescripcion(SelectedCiudadDTO.getDescripcion());
        setCciudadId(SelectedCiudadDTO.getId());
        setCciudadStatus(SelectedCiudadDTO.getStatus());
        setCciudaddeptoId(SelectedCiudadDTO.getIddepto());
        setCciudadmunicipioId(SelectedCiudadDTO.getIdmunicipio());
        CciudadEdit = false;
    }

    public void cancelarCiudad() {
        ClearCiudad();
    }

    public void loadCtipoCiudadLink() {
        loadCtipoCiudad();
        loadDeptoCiudad();
        nextIdCiudad();
        ClearCiudad();

    }

    public void loadMunicpioByIdDepto() {

        JSONObject param = new JSONObject();
        param.put("idDepto", getCciudaddeptoId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/fcmunicipiobyiddepto";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        selectedCiudadDeptoMunicipioCbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmunicipio c = new Cmunicipio();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setIdDepto(arr.getJSONObject(i).get("iddepto").toString());
            c.setDescripcionDepto(arr.getJSONObject(i).get("descripciondepto").toString());
            selectedCiudadDeptoMunicipioCbx.add(c);

        }

    }

    private void loadDeptoCiudad() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/fcdepto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        selectedCiudadDeptoCbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cdepto c = new Cdepto();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            selectedCiudadDeptoCbx.add(c);

        }
    }

    public void DCtipoCiudad() {
        JSONObject param = new JSONObject();
        param.put("id", getCciudadId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cciudad/dcciudad";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoCiudad();
        ClearCiudad();

    }

    public void SCtipoCiudad() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCciudadDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCciudadStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (ValidNullString(getCciudaddeptoId())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un Departamento");
        }
        if (ValidNullString(getCciudadmunicipioId())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un Municipio");
        }

        if (!validate) {
            param.put("id", getCciudadId());
            param.put("descripcion", getCciudadDescripcion());
            param.put("status", getCciudadStatus());
            param.put("id_municipio", getCciudadmunicipioId());
            param.put("id_depto", getCciudaddeptoId());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cciudad/scciudad";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoCiudad();
            ClearCiudad();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Cliente :::::::::::::......................*/
    private boolean CclienteEdit = true;
    private String CclienteId;
    private String Cclientenombre;
    private String Cclientedireccion;
    private String CclienteidDepto;
    private String CclienteidMunicipio;
    private String CclienteidCiudad;
    private String CclienteregistroFiscal;
    private String Cclientenit;
    private String CclienteGiro;
    private String Cclientetelefono1;
    private String Cclientetelefono2;
    private String Cclientefax;
    private String CclientelimiteDeCredito;
    private String Cclienteemail;
    private String Cclientecomentarios;
    private String Cclientepercepcion;
    private Ccliente SelectedClienteDTO;
    private List<Ccliente> CtipoClienteList;
    private List<Cdepto> selectedClienteDeptoCbx;
    private List<Cmunicipio> selectedClienteDeptoMunicipioCbx;
    private List<Cciudad> selectedClienteCiudadCbx;
    private String pageCTipoCliente = "";

    public void loadClienteCiudad() {
        JSONObject param = new JSONObject();
        param.put("iddepto", getCclienteidDepto());
        param.put("idmuni", getCclienteidMunicipio());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cciudad/fcciudadbyiddeptoidmuni";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        selectedClienteCiudadCbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cciudad c = new Cciudad();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            selectedClienteCiudadCbx.add(c);

        }
    }

    private void loadCtipoCliente() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/fccliente";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoClienteList = new ArrayList<>();
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
            CtipoClienteList.add(c);

        }

    }

    private void nextIdCliente() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCclienteId(obj.get("dato").toString());

    }

    public void ClearCliente() {
        setCclienteId("");
        setCclientecomentarios("");
        setCclientedireccion("");
        setCclienteemail("");
        setCclientefax("");
        setCclienteidCiudad("");
        setCclienteidDepto("");
        setCclienteGiro("");
        setCclienteidMunicipio("");

        setCclientelimiteDeCredito("");
        setCclientenombre("");
        setCclientenit("");
        setCclientepercepcion("");
        setCclienteregistroFiscal("");
        setCclientetelefono1("");
        setCclientetelefono2("");
        SelectedClienteDTO = new Ccliente();
        selectedClienteDeptoMunicipioCbx = new ArrayList<>();
        selectedClienteCiudadCbx = new ArrayList<>();
        nextIdCliente();
        CclienteEdit = true;
    }

    public void selectedRowCliente() {
        setCclienteId(SelectedClienteDTO.getId());

        setCclientecomentarios(SelectedClienteDTO.getComentarios());
        setCclientedireccion(SelectedClienteDTO.getDireccion());
        setCclienteemail(SelectedClienteDTO.getEmail());
        setCclientefax(SelectedClienteDTO.getFax());
        setCclienteidCiudad(SelectedClienteDTO.getIdCiudad());
        setCclienteidDepto(SelectedClienteDTO.getIdDepto());
        setCclienteGiro(SelectedClienteDTO.getGiro());
        setCclienteidMunicipio(SelectedClienteDTO.getIdMunicipio());
        setCclientelimiteDeCredito(SelectedClienteDTO.getLimiteDeCredito());
        setCclientenombre(SelectedClienteDTO.getNombre());
        setCclientenit(SelectedClienteDTO.getNit());
        setCclientepercepcion(SelectedClienteDTO.getPercepcion());
        setCclienteregistroFiscal(SelectedClienteDTO.getRegistroFiscal());
        setCclientetelefono1(SelectedClienteDTO.getTelefono1());
        setCclientetelefono2(SelectedClienteDTO.getTelefono2());
        CclienteEdit = false;
    }

    public void cancelarCliente() {
        ClearCliente();
    }

    public void loadMunicpioByIdDeptoCliente() {

        JSONObject param = new JSONObject();
        param.put("idDepto", getCclienteidDepto());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmunicipio/fcmunicipiobyiddepto";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        selectedClienteDeptoMunicipioCbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmunicipio c = new Cmunicipio();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setIdDepto(arr.getJSONObject(i).get("iddepto").toString());
            c.setDescripcionDepto(arr.getJSONObject(i).get("descripciondepto").toString());
            selectedClienteDeptoMunicipioCbx.add(c);

        }

    }

    private void loadDeptoCliente() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cdepto/fcdepto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        selectedClienteDeptoCbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cdepto c = new Cdepto();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            selectedClienteDeptoCbx.add(c);

        }
    }

    public void loadCtipoClienteLink() {
        loadCtipoCliente();
        loadDeptoCliente();
        nextIdCliente();
        ClearCliente();
    }

    public List<Ccliente> getClientes() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/fccliente";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoClienteList = new ArrayList<>();
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
            CtipoClienteList.add(c);
        }
        loadDeptoCliente();
        nextIdCliente();
        ClearCliente();
        return CtipoClienteList;
    }

    public void selectClienteFromDialog(Ccliente cliente) {
        RequestContext.getCurrentInstance().execute("PF('dialoclienteSearch').hide()");
        //el cliente viene lleno tratar de llenar el formulario con este valor, selectedRowClienteSearch
    }

    public void DCtipoCliente() {
        JSONObject param = new JSONObject();
        param.put("id", getCclienteId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/dccliente";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoCliente();
        ClearCliente();

    }

    public void SCtipoCliente() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCclientenombre())) {
            validate = true;
            addsimplemessageserror("Campo Nombre es requerido");
        }

        if (!validate) {
            param.put("id", getCclienteId());
            param.put("nombre", getCclientenombre());
            param.put("direccion", getCclientedireccion());
            param.put("idDepto", getCclienteidDepto());
            param.put("idMunicipio", getCclienteidMunicipio());
            param.put("idCiudad", getCclienteidCiudad());
            param.put("registroFiscal", getCclienteregistroFiscal());
            param.put("nit", getCclientenit());
            param.put("giro", getCclienteGiro());
            param.put("telefono1", getCclientetelefono1());
            param.put("telefono2", getCclientetelefono2());
            param.put("fax", getCclientefax());
            param.put("limiteDeCredito", getCclientelimiteDeCredito());
            param.put("email", getCclienteemail());
            param.put("comentarios", getCclientecomentarios());
            param.put("percepcion", getCclientepercepcion());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ccliente/scliente";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoCliente();
            ClearCliente();
        }

    }
    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Prodcuto :::::::::::::......................*/
    String cpid = "";
    String cpid_marca = "";
    String cpid_modelo = "";
    String cpdescripcion = "";
    String cpid_categoria = "";
    String cpstock = "";
    String cpservicio = "";
    String cpstock_minimo = "";
    String cpstock_maximo = "";
    String cpsuspendido = "";
    String cpcosto_compra = "";
    String cpcosto_fob = "";
    String cpcosto_contable = "";
    String cpultimo_costo_s_impuesto = "";
    String cpultimo_costo_c_impuesto = "";
    String cpcosto_prom_s_impuesto = "";
    String cpcosto_prom_c_impuesto = "";
    String cpcosto_anterior_c_impuesto = "";
    String cputilidad1 = "";
    String cpprecio1 = "";
    String cputilidad2 = "";
    String cpprecio2 = "";
    String cputildad3 = "";
    String cpprecio3 = "";
    String cpoem = "";
    String codigo = "";
    String pageProducto = "";
    Date cpfecha_recepcion;
    private Cproducto SelectedProductoDTO;
    private List<Cproducto> CProductoList;
    private List<Cmodelo> CpcModeloListcbx;
    private List<Cmarca> CpcModeloMarcaListcbx;
    private List<Ccategoria> CpccategoriaListcbx;

    public void loadCtipoModeloBymarca_fnd() {
        JSONObject param = new JSONObject();
        param.put("idMarca", getCpid_marca_fnd());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/fcmodelobyidmarca";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CpcModeloListcbx_fnd = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmodelo c = new Cmodelo();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpcModeloListcbx_fnd.add(c);

        }

    }

    public void loadCtipoModeloBymarca() {
        JSONObject param = new JSONObject();
        param.put("idMarca", getCpid_marca());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmodelo/fcmodelobyidmarca";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CpcModeloListcbx = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cmodelo c = new Cmodelo();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpcModeloListcbx.add(c);

        }

    }

    private void loadcbxCategoriaProdcuto_fnd() {

        CpccategoriaListcbx_fnd = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/fccategoria";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Ccategoria c = new Ccategoria();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpccategoriaListcbx_fnd.add(c);

        }

    }

    private void loadcbxCategoriaProdcuto() {

        CpccategoriaListcbx = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/fccategoria";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Ccategoria c = new Ccategoria();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpccategoriaListcbx.add(c);

        }

    }

    private void loadcbxMarcaProdcuto() {

        CpcModeloMarcaListcbx = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/fcmarca";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Cmarca c = new Cmarca();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpcModeloMarcaListcbx.add(c);

        }

    }

    private void loadcbxMarcaProdcuto_fnd() {

        CpcModeloMarcaListcbx_fnd = new ArrayList<>();
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cmarca/fcmarca";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        for (int i = 0; i < arr.length(); i++) {
            Cmarca c = new Cmarca();
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CpcModeloMarcaListcbx_fnd.add(c);

        }

    }

    public void loadCProducto() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/fcproducto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CProductoList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cproducto c = new Cproducto();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setCosto_anterior_c_impuesto(arr.getJSONObject(i).get("costo_anterior_c_impuesto").toString());
            c.setCosto_compra(arr.getJSONObject(i).get("costo_compra").toString());
            c.setCosto_contable(arr.getJSONObject(i).get("costo_contable").toString());
            c.setCosto_fob(arr.getJSONObject(i).get("costo_fob").toString());
            c.setCosto_prom_c_impuesto(arr.getJSONObject(i).get("costo_prom_c_impuesto").toString());
            c.setCosto_prom_s_impuesto(arr.getJSONObject(i).get("costo_prom_s_impuesto").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId_categoria(arr.getJSONObject(i).get("id_categoria").toString());
            c.setId_marca(arr.getJSONObject(i).get("id_marca").toString());
            c.setId_modelo(arr.getJSONObject(i).get("id_modelo").toString());
            c.setOem(arr.getJSONObject(i).get("oem").toString());
            c.setPrecio1(arr.getJSONObject(i).get("precio1").toString());
            c.setPrecio2(arr.getJSONObject(i).get("precio2").toString());
            c.setPrecio3(arr.getJSONObject(i).get("precio3").toString());
            c.setServicio(arr.getJSONObject(i).get("servicio").toString());
            c.setStock(arr.getJSONObject(i).get("stock").toString());
            c.setStock_maximo(arr.getJSONObject(i).get("stock_maximo").toString());
            c.setStock_minimo(arr.getJSONObject(i).get("stock_minimo").toString());
            c.setSuspendido(arr.getJSONObject(i).get("suspendido").toString());
            c.setUltimo_costo_c_impuesto(arr.getJSONObject(i).get("ultimo_costo_c_impuesto").toString());
            c.setUltimo_costo_s_impuesto(arr.getJSONObject(i).get("ultimo_costo_s_impuesto").toString());
            c.setUtildad3(arr.getJSONObject(i).get("utildad3").toString());
            c.setUtilidad1(arr.getJSONObject(i).get("utilidad1").toString());
            c.setUtilidad2(arr.getJSONObject(i).get("utilidad2").toString());
            c.setDesc_categoria(arr.getJSONObject(i).get("desc_categoria").toString());
            c.setDesc_marca(arr.getJSONObject(i).get("desc_marca").toString());
            c.setDesc_modelo(arr.getJSONObject(i).get("desc_modelo").toString());
            c.setCodigo(arr.getJSONObject(i).get("codigo").toString());
            c.setFecha_recepcion(arr.getJSONObject(i).get("fecha_recepcion").toString());
            CProductoList.add(c);

        }

    }

    public void loadCProducto_fnd() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/fcproducto";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CProductoList_fnd = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cproducto_fnd c = new Cproducto_fnd();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setCosto_anterior_c_impuesto(arr.getJSONObject(i).get("costo_anterior_c_impuesto").toString());
            c.setCosto_compra(arr.getJSONObject(i).get("costo_compra").toString());
            c.setCosto_contable(arr.getJSONObject(i).get("costo_contable").toString());
            c.setCosto_fob(arr.getJSONObject(i).get("costo_fob").toString());
            c.setCosto_prom_c_impuesto(arr.getJSONObject(i).get("costo_prom_c_impuesto").toString());
            c.setCosto_prom_s_impuesto(arr.getJSONObject(i).get("costo_prom_s_impuesto").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setId_categoria(arr.getJSONObject(i).get("id_categoria").toString());
            c.setId_marca(arr.getJSONObject(i).get("id_marca").toString());
            c.setId_modelo(arr.getJSONObject(i).get("id_modelo").toString());
            c.setOem(arr.getJSONObject(i).get("oem").toString());
            c.setPrecio1(arr.getJSONObject(i).get("precio1").toString());
            c.setPrecio2(arr.getJSONObject(i).get("precio2").toString());
            c.setPrecio3(arr.getJSONObject(i).get("precio3").toString());
            c.setServicio(arr.getJSONObject(i).get("servicio").toString());
            c.setStock(arr.getJSONObject(i).get("stock").toString());
            c.setStock_maximo(arr.getJSONObject(i).get("stock_maximo").toString());
            c.setStock_minimo(arr.getJSONObject(i).get("stock_minimo").toString());
            c.setSuspendido(arr.getJSONObject(i).get("suspendido").toString());
            c.setUltimo_costo_c_impuesto(arr.getJSONObject(i).get("ultimo_costo_c_impuesto").toString());
            c.setUltimo_costo_s_impuesto(arr.getJSONObject(i).get("ultimo_costo_s_impuesto").toString());
            c.setUtildad3(arr.getJSONObject(i).get("utildad3").toString());
            c.setUtilidad1(arr.getJSONObject(i).get("utilidad1").toString());
            c.setUtilidad2(arr.getJSONObject(i).get("utilidad2").toString());
            c.setDesc_categoria(arr.getJSONObject(i).get("desc_categoria").toString());
            c.setDesc_marca(arr.getJSONObject(i).get("desc_marca").toString());
            c.setDesc_modelo(arr.getJSONObject(i).get("desc_modelo").toString());
            c.setCodigo(arr.getJSONObject(i).get("codigo").toString());
            c.setFecha_recepcion(arr.getJSONObject(i).get("fecha_recepcion").toString());
            CProductoList_fnd.add(c);
        }
    }

    private void nextIdCProducto() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCpid(obj.get("dato").toString());

    }

    public void ClearCproducto() {
        setCpcosto_anterior_c_impuesto("");
        setCpcosto_compra("");
        setCpcosto_contable("");
        setCpcosto_fob("");
        setCpcosto_prom_c_impuesto("");
        setCpcosto_prom_s_impuesto("");
        setCpdescripcion("");
        setCpid("");
        setCpid_categoria("");
        setCpid_marca("");
        setCpid_modelo("");
        setCpoem("");
        setCpprecio1("");
        setCpprecio2("");
        setCpprecio3("");
        setCodigo("");
        // setCprodId("");
        setCprodStatus("");
        setCpservicio("");
        setCpstock("");
        setCpstock_maximo("");
        setCpsuspendido("");
        setCpstock_minimo("");
        setCpultimo_costo_c_impuesto("");
        setCpultimo_costo_s_impuesto("");
        setCputildad3("");
        setCputilidad1("");
        setCputilidad2("");
        setCpfecha_recepcion(new GregorianCalendar().getTime());

        SelectedProductoDTO = new Cproducto();
        nextIdCProducto();
        CprodEdit = (false);
        //CclienteEdit=true;
    }

    public void ClearCproducto_fnd() {
        setCpcosto_anterior_c_impuesto_fnd("");
        setCpcosto_compra_fnd("");
        setCpcosto_contable_fnd("");
        setCpcosto_fob_fnd("");
        setCpcosto_prom_c_impuesto_fnd("");
        setCpcosto_prom_s_impuesto_fnd("");
        setCpdescripcion_fnd("");
        setCpid_fnd("");
        setCpid_categoria_fnd("");
        setCpid_marca_fnd("");
        setCpid_modelon_fnd("");
        setCpoem_fnd("");
        setCpprecio1_fnd("");
        setCpprecio2_fnd("");
        setCpprecio3_fnd("");
        setCodigo_fnd("");
        // setCprodId("");
        //setCprodStatus("");
        setCpservicio_fnd("");
        setCpstock_fnd("");
        setCpstock_maximo_fnd("");
        setCpsuspendido_fnd("");
        setCpstock_minimo_fnd("");
        setCpultimo_costo_c_impuesto_fnd("");
        setCpultimo_costo_s_impuesto_fnd("");
        setCputildad3_fnd("");
        setCputilidad1_fnd("");
        setCputilidad2_fnd("");
        setCpfecha_recepcion_fnd(new GregorianCalendar().getTime());

        SelectedProductoDTO_fnd = new Cproducto_fnd();
        //nextIdCProducto();
        //CprodEdit = (false);
        //CclienteEdit=true;
    }

    public void selectedRowProducto() throws ParseException {
        setCpcosto_anterior_c_impuesto(SelectedProductoDTO.getCosto_anterior_c_impuesto());
        setCpcosto_compra(SelectedProductoDTO.getCosto_compra());
        setCpcosto_contable(SelectedProductoDTO.getCosto_contable());
        setCpcosto_fob(SelectedProductoDTO.getCosto_fob());
        setCpcosto_prom_c_impuesto(SelectedProductoDTO.getCosto_prom_c_impuesto());
        setCpcosto_prom_s_impuesto(SelectedProductoDTO.getCosto_prom_s_impuesto());
        setCpdescripcion(SelectedProductoDTO.getDescripcion());
        setCpid(SelectedProductoDTO.getId());
        setCpid_categoria(SelectedProductoDTO.getId_categoria());
        setCpid_marca(SelectedProductoDTO.getId_marca());
        if (!getCpid_marca().equals("")) {
            loadCtipoModeloBymarca();
        }

        setCpid_modelo(SelectedProductoDTO.getId_modelo());
        setCpoem(SelectedProductoDTO.getOem());
        setCpprecio1(SelectedProductoDTO.getPrecio1());
        setCpprecio2(SelectedProductoDTO.getPrecio2());
        setCpprecio3(SelectedProductoDTO.getPrecio3());
        setCodigo(SelectedProductoDTO.getCodigo());
        //setCprodId(SelectedProductoDTO.get);
        //setCprodStatus(SelectedProductoDTO.get);
        setCpservicio(SelectedProductoDTO.getServicio());
        setCpstock(SelectedProductoDTO.getStock());
        setCpstock_maximo(SelectedProductoDTO.getStock_maximo());
        setCpsuspendido(SelectedProductoDTO.getSuspendido());
        setCpstock_minimo(SelectedProductoDTO.getStock_minimo());
        setCpultimo_costo_c_impuesto(SelectedProductoDTO.getUltimo_costo_c_impuesto());
        setCpultimo_costo_s_impuesto(SelectedProductoDTO.getUltimo_costo_s_impuesto());
        setCputildad3(SelectedProductoDTO.getUtildad3());
        setCputilidad1(SelectedProductoDTO.getUtilidad1());
        setCputilidad2(SelectedProductoDTO.getUtilidad2());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        setCpfecha_recepcion(df.parse(SelectedProductoDTO.getFecha_recepcion()));
        CprodEdit = (false);

    }

    public void selectedRowProducto_fnd() throws ParseException {
        setCpcosto_anterior_c_impuesto_fnd(SelectedProductoDTO_fnd.getCosto_anterior_c_impuesto());
        setCpcosto_compra_fnd(SelectedProductoDTO_fnd.getCosto_compra());
        setCpcosto_contable_fnd(SelectedProductoDTO_fnd.getCosto_contable());
        setCpcosto_fob_fnd(SelectedProductoDTO_fnd.getCosto_fob());
        setCpcosto_prom_c_impuesto_fnd(SelectedProductoDTO_fnd.getCosto_prom_c_impuesto());
        setCpcosto_prom_s_impuesto_fnd(SelectedProductoDTO_fnd.getCosto_prom_s_impuesto());
        setCpdescripcion_fnd(SelectedProductoDTO_fnd.getDescripcion());
        setCpid_fnd(SelectedProductoDTO_fnd.getId());
        setCpid_categoria_fnd(SelectedProductoDTO_fnd.getId_categoria());
        setCpid_marca_fnd(SelectedProductoDTO_fnd.getId_marca());
        if (!getCpid_marca_fnd().equals("")) {
            loadCtipoModeloBymarca_fnd();
        }

        setCpid_modelon_fnd(SelectedProductoDTO_fnd.getId_modelo());
        setCpoem_fnd(SelectedProductoDTO_fnd.getOem());
        setCpprecio1_fnd(SelectedProductoDTO_fnd.getPrecio1());
        setCpprecio2_fnd(SelectedProductoDTO_fnd.getPrecio2());
        setCpprecio3_fnd(SelectedProductoDTO_fnd.getPrecio3());
        setCodigo_fnd(SelectedProductoDTO_fnd.getCodigo());
        //setCprodId(SelectedProductoDTO_fnd.get);
        //setCprodStatus(SelectedProductoDTO_fnd.get);
        setCpservicio_fnd(SelectedProductoDTO_fnd.getServicio());
        setCpstock_fnd(SelectedProductoDTO_fnd.getStock());
        setCpstock_maximo_fnd(SelectedProductoDTO_fnd.getStock_maximo());
        setCpsuspendido_fnd(SelectedProductoDTO_fnd.getSuspendido());
        setCpstock_minimo_fnd(SelectedProductoDTO_fnd.getStock_minimo());
        setCpultimo_costo_c_impuesto_fnd(SelectedProductoDTO_fnd.getUltimo_costo_c_impuesto());
        setCpultimo_costo_s_impuesto_fnd(SelectedProductoDTO_fnd.getUltimo_costo_s_impuesto());
        setCputildad3_fnd(SelectedProductoDTO_fnd.getUtildad3());
        setCputilidad1_fnd(SelectedProductoDTO_fnd.getUtilidad1());
        setCputilidad2_fnd(SelectedProductoDTO_fnd.getUtilidad2());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        setCpfecha_recepcion_fnd(df.parse(SelectedProductoDTO_fnd.getFecha_recepcion()));
        // CprodEdit = (false);

    }

    public void cancelarProdcuto_fnd() {
        ClearCproducto_fnd();
        // CprodEdit = (true);
    }

    public void cancelarProdcuto() {
        ClearCproducto();
        CprodEdit = (true);
    }

    public void loadCProductoLink_fnd() {
        loadCProducto_fnd();
        ClearCproducto_fnd();
        loadcbxMarcaProdcuto_fnd();
        loadcbxCategoriaProdcuto_fnd();
        //CprodEdit = (true);
    }

    public void loadCProductoLink() {
        loadCProducto();
        nextIdCProducto();
        ClearCproducto();
        loadcbxMarcaProdcuto();
        loadcbxCategoriaProdcuto();
        CprodEdit = (true);
    }

    public void DCProdcuto() {
        JSONObject param = new JSONObject();
        param.put("id", getCpid());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/dcproducto";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCProducto();
        nextIdCProducto();
        ClearCproducto();
        CprodEdit = (false);

    }

    public void SCproducto() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCodigo())) {
            validate = true;
            addsimplemessageserror("Campo Codigo es requerido");
        }
        if (ValidNullString(getCpdescripcion())) {
            validate = true;
            addsimplemessageserror("Campo Descripcion es requerido");
        }

        if (!validate) {
            param.put("id", getCpid());
            param.put("id_marca", (getCpid_marca() != null ? getCpid_marca() : ""));
            param.put("id_modelo", (getCpid_modelo() != null ? getCpid_modelo() : ""));
            param.put("descripcion", getCpdescripcion());
            param.put("id_categoria", (getCpid_categoria() != null ? getCpid_categoria() : ""));
            param.put("stock", getCpstock());
            param.put("servicio", getCpservicio());
            param.put("stock_minimo", getCpstock_minimo());
            param.put("stock_maximo", getCpstock_maximo());
            param.put("suspendido", getCpsuspendido());
            param.put("costo_compra", getCpcosto_compra());
            param.put("costo_fob", getCpcosto_fob());
            param.put("costo_contable", getCpcosto_contable());
            param.put("ultimo_costo_s_impuesto", getCpultimo_costo_s_impuesto());
            param.put("ultimo_costo_c_impuesto", getCpultimo_costo_c_impuesto());
            param.put("costo_prom_s_impuesto", getCpcosto_prom_s_impuesto());
            param.put("costo_prom_c_impuesto", getCpcosto_prom_c_impuesto());
            param.put("costo_anterior_c_impuesto", getCpcosto_anterior_c_impuesto());
            param.put("utilidad1", getCputilidad1());
            param.put("precio1", getCpprecio1());
            param.put("utilidad2", getCputilidad2());
            param.put("precio2", getCpprecio2());
            param.put("utildad3", getCputildad3());
            param.put("precio3", getCpprecio3());
            param.put("oem", getCpoem());
            param.put("codigo", getCodigo());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            param.put("fecha_recepcion", df.format(getCpfecha_recepcion()));

            String url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/scproducto";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCProducto();
            nextIdCProducto();
            ClearCproducto();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Categoria :::::::::::::......................*/
    private boolean CcategoriaEdit = true;
    private String CcategoriaId;
    private String CcategoriaDescripcion;
    private String CcategoriaStatus;
    private Ccategoria SelectedCategoriaDTO;
    private List<Ccategoria> CtipoCategoriaList;
    private String pageCTipoCategoria = "";

    private void loadCtipoCategoria() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/fccategoria";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoCategoriaList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Ccategoria c = new Ccategoria();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            CtipoCategoriaList.add(c);

        }

    }

    private void nextIdCategoria() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCcategoriaId(obj.get("dato").toString());

    }

    public void ClearCategoria() {
        setCcategoriaDescripcion("");
        setCcategoriaId("");
        setCcategoriaStatus("0");
        SelectedCategoriaDTO = new Ccategoria();
        nextIdCategoria();
        CcategoriaEdit = true;
    }

    public void selectedRowCategoria() {
        setCcategoriaDescripcion(SelectedCategoriaDTO.getDescripcion());
        setCcategoriaId(SelectedCategoriaDTO.getId());
        setCcategoriaStatus(SelectedCategoriaDTO.getStatus());
        CcategoriaEdit = false;
    }

    public void cancelarCategoria() {
        System.out.println("ID de la empresa" + getId_empresa());
        ClearCategoria();
    }

    public void loadCtipoCategoriaLink() {
        loadCtipoCategoria();
        nextIdCategoria();
        ClearCategoria();
    }

    public void DCtipoCategoria() {
        JSONObject param = new JSONObject();
        param.put("id", getCcategoriaId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/dccategoria";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoCategoria();
        ClearCategoria();

    }

    public void SCtipoCategoria() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCcategoriaDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCcategoriaStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCcategoriaId());
            param.put("descripcion", getCcategoriaDescripcion());
            param.put("status", getCcategoriaStatus());
            String url = getHttpSegment() + "/WsRestFullJap/jap/ccategoria/sccategoria";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoCategoria();
            ClearCategoria();
        }

    }

    /*...........::::::::::::::::::::::::. Metodos y Variables para Catalogo Proveedor :::::::::::::......................*/
    private boolean CproveedorEdit = true;
    private String CproveedorId;
    private String CproveedorDescripcion;
    private String CproveedorStatus;
    private String Cproveedordireccion;
    private String Cproveedortel1;
    private String Cproveedortel2;
    private String Cproveedormoto;
    private Cproveedor SelectedProveedorDTO;
    private List<Cproveedor> CtipoProveedorList;
    private String pageCTipoProveedor = "";

    private void loadCtipoProveedor() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproveedor/fcproveedor";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        CtipoProveedorList = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cproveedor c = new Cproveedor();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            c.setDireccion(arr.getJSONObject(i).get("direccion").toString());
            c.setTel1(arr.getJSONObject(i).get("tel1").toString());
            c.setTel2(arr.getJSONObject(i).get("tel2").toString());
            c.setProv_moto(arr.getJSONObject(i).get("provMoto").toString());
            CtipoProveedorList.add(c);
        }

    }

    private void nextIdProveedor() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproveedor/nextid";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        setCproveedorId(obj.get("dato").toString());

    }

    public void ClearProveedor() {
        setCproveedorDescripcion("");
        setCproveedorId("");
        setCproveedorStatus("0");
        setCproveedordireccion("");
        setCproveedormoto("0");
        setCproveedortel1("");
        setCproveedortel2("");
        SelectedProveedorDTO = new Cproveedor();
        nextIdProveedor();
        CproveedorEdit = true;
    }

    public void selectedRowProveedor() {
        setCproveedorDescripcion(SelectedProveedorDTO.getDescripcion());
        setCproveedorId(SelectedProveedorDTO.getId());
        setCproveedorStatus(SelectedProveedorDTO.getStatus());
        setCproveedordireccion(SelectedProveedorDTO.getDireccion());
        setCproveedormoto(SelectedProveedorDTO.getProv_moto());
        setCproveedortel1(SelectedProveedorDTO.getTel1());
        setCproveedortel2(SelectedProveedorDTO.getTel2());
        CproveedorEdit = false;
    }

    public void cancelarProveedor() {
        ClearProveedor();
    }

    public void loadCtipoProveedorLink() {
        loadCtipoProveedor();
        nextIdProveedor();
        ClearProveedor();
    }

    public void DCtipoProveedor() {
        JSONObject param = new JSONObject();
        param.put("id", getCproveedorId());
        String url = getHttpSegment() + "/WsRestFullJap/jap/cproveedor/dcproveedor";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        addsimplemessages(obj.get("dato").toString());
        loadCtipoProveedor();
        ClearProveedor();

    }

    public void SCtipoProveedor() {
        JSONObject param = new JSONObject();
        boolean validate = false;

        if (ValidNullString(getCproveedorDescripcion())) {
            validate = true;
            addsimplemessageserror("Campo descripcion es requerido");
        }
        if (ValidNullString(getCproveedorStatus())) {
            validate = true;
            addsimplemessageserror("Debe de seleccionar un estado");
        }
        if (!validate) {
            param.put("id", getCproveedorId());
            param.put("descripcion", getCproveedorDescripcion());
            param.put("status", getCproveedorStatus());
            param.put("tel1", getCproveedortel1());
            param.put("tel2", getCproveedortel2());
            param.put("direccion", getCproveedordireccion());
            param.put("prov_moto", getCproveedormoto());
            String url = getHttpSegment() + "/WsRestFullJap/jap/cproveedor/scproveedor";
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            addsimplemessages(obj.get("dato").toString());
            loadCtipoProveedor();
            ClearProveedor();
        }

    }

    /*variables busqueda de productos*/
    String cpid_fnd = "";
    String cpid_marca_fnd = "";
    String cpid_modelon_fnd = "";
    String cpdescripcion_fnd = "";
    String cpid_categoria_fnd = "";
    String cpstock_fnd = "";
    String cpservicio_fnd = "";
    String cpstock_minimo_fnd = "";
    String cpstock_maximo_fnd = "";
    String cpsuspendido_fnd = "";
    String cpcosto_compra_fnd = "";
    String cpcosto_fob_fnd = "";
    String cpcosto_contable_fnd = "";
    String cpultimo_costo_s_impuesto_fnd = "";
    String cpultimo_costo_c_impuesto_fnd = "";
    String cpcosto_prom_s_impuesto_fnd = "";
    String cpcosto_prom_c_impuesto_fnd = "";
    String cpcosto_anterior_c_impuesto_fnd = "";
    String cputilidad1_fnd = "";
    String cpprecio1_fnd = "";
    String cputilidad2_fnd = "";
    String cpprecio2_fnd = "";
    String cputildad3_fnd = "";
    String cpprecio3_fnd = "";
    String cpoem_fnd = "";
    String codigo_fnd = "";
    String pageProducto_fnd = "";

    Date cpfecha_recepcion_fnd;
    private Cproducto_fnd SelectedProductoDTO_fnd;
    private List<Cproducto_fnd> SelectedProductoDTO_fnd_facturacion;
    private List<Cproducto_fnd> CProductoList_fnd;
    private List<Cproducto_fnd> CProductoList_fnd_filter;
    private List<Cmodelo> CpcModeloListcbx_fnd;
    private List<Cmarca> CpcModeloMarcaListcbx_fnd;
    private List<Ccategoria> CpccategoriaListcbx_fnd;

    /*
     *   Métodos para Orden de Entrega
     */
    private Ccliente clienteOE;
    private Cfpago fpago;
    private List<Cfpago> listFpago;
    private String selectedIdFormaPago;

    public void showNewOE() {
        this.clienteOE = new Ccliente();
        loadFormaPago();
        setEmpleadoCheck(getEmpleado().getNombre());
        setSucursalCheck(getEmpleado().getIdSucursal());
    }

    public void c_fnd_productofacturacion() {

        loadCProducto_fnd();
        //SelectedProductoDTO_fnd_facturacion =   new ArrayList<>();
    }

    public void loadFormaPago() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/fcformadepago";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        listFpago = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            fpago = new Cfpago();
            fpago.setId(arr.getJSONObject(i).get("id").toString());
            fpago.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            fpago.setStatus(arr.getJSONObject(i).get("status").toString());
            listFpago.add(fpago);
        }
    }

    public void tes_lista() {

        System.out.println(getSelectedProductoDTO_fnd_facturacion().size());

    }

    public Ccliente getClienteOE() {
        return clienteOE;
    }

    public void setClienteOE(Ccliente clienteOE) {
        this.clienteOE = clienteOE;
    }

    public String getSelectedIdFormaPago() {
        return selectedIdFormaPago;
    }

    public void setSelectedIdFormaPago(String selectedIdFormaPago) {
        this.selectedIdFormaPago = selectedIdFormaPago;
    }

    public Cfpago getFpago() {
        return fpago;
    }

    public void setFpago(Cfpago fpago) {
        this.fpago = fpago;
    }

    public List<Cfpago> getListFpago() {
        return listFpago;
    }

    public void setListFpago(List<Cfpago> listFpago) {
        this.listFpago = listFpago;
    }

    public boolean isCproveedorEdit() {
        return CproveedorEdit;
    }

    public String getCpid_fnd() {
        return cpid_fnd;
    }

    public void setCpid_fnd(String cpid_fnd) {
        this.cpid_fnd = cpid_fnd;
    }

    public String getCpid_marca_fnd() {
        return cpid_marca_fnd;
    }

    public void setCpid_marca_fnd(String cpid_marca_fnd) {
        this.cpid_marca_fnd = cpid_marca_fnd;
    }

    public String getCpid_modelon_fnd() {
        return cpid_modelon_fnd;
    }

    public void setCpid_modelon_fnd(String cpid_modelon_fnd) {
        this.cpid_modelon_fnd = cpid_modelon_fnd;
    }

    public String getCpdescripcion_fnd() {
        return cpdescripcion_fnd;
    }

    public void setCpdescripcion_fnd(String cpdescripcion_fnd) {
        this.cpdescripcion_fnd = cpdescripcion_fnd;
    }

    public String getCpid_categoria_fnd() {
        return cpid_categoria_fnd;
    }

    public void setCpid_categoria_fnd(String cpid_categoria_fnd) {
        this.cpid_categoria_fnd = cpid_categoria_fnd;
    }

    public String getCpstock_fnd() {
        return cpstock_fnd;
    }

    public void setCpstock_fnd(String cpstock_fnd) {
        this.cpstock_fnd = cpstock_fnd;
    }

    public String getCpservicio_fnd() {
        return cpservicio_fnd;
    }

    public void setCpservicio_fnd(String cpservicio_fnd) {
        this.cpservicio_fnd = cpservicio_fnd;
    }

    public String getCpstock_minimo_fnd() {
        return cpstock_minimo_fnd;
    }

    public void setCpstock_minimo_fnd(String cpstock_minimo_fnd) {
        this.cpstock_minimo_fnd = cpstock_minimo_fnd;
    }

    public String getCpstock_maximo_fnd() {
        return cpstock_maximo_fnd;
    }

    public void setCpstock_maximo_fnd(String cpstock_maximo_fnd) {
        this.cpstock_maximo_fnd = cpstock_maximo_fnd;
    }

    public String getCpsuspendido_fnd() {
        return cpsuspendido_fnd;
    }

    public void setCpsuspendido_fnd(String cpsuspendido_fnd) {
        this.cpsuspendido_fnd = cpsuspendido_fnd;
    }

    public String getCpcosto_compra_fnd() {
        return cpcosto_compra_fnd;
    }

    public void setCpcosto_compra_fnd(String cpcosto_compra_fnd) {
        this.cpcosto_compra_fnd = cpcosto_compra_fnd;
    }

    public String getCpcosto_fob_fnd() {
        return cpcosto_fob_fnd;
    }

    public void setCpcosto_fob_fnd(String cpcosto_fob_fnd) {
        this.cpcosto_fob_fnd = cpcosto_fob_fnd;
    }

    public String getCpcosto_contable_fnd() {
        return cpcosto_contable_fnd;
    }

    public void setCpcosto_contable_fnd(String cpcosto_contable_fnd) {
        this.cpcosto_contable_fnd = cpcosto_contable_fnd;
    }

    public String getCpultimo_costo_s_impuesto_fnd() {
        return cpultimo_costo_s_impuesto_fnd;
    }

    public void setCpultimo_costo_s_impuesto_fnd(String cpultimo_costo_s_impuesto_fnd) {
        this.cpultimo_costo_s_impuesto_fnd = cpultimo_costo_s_impuesto_fnd;
    }

    public String getCpultimo_costo_c_impuesto_fnd() {
        return cpultimo_costo_c_impuesto_fnd;
    }

    public void setCpultimo_costo_c_impuesto_fnd(String cpultimo_costo_c_impuesto_fnd) {
        this.cpultimo_costo_c_impuesto_fnd = cpultimo_costo_c_impuesto_fnd;
    }

    public String getCpcosto_prom_s_impuesto_fnd() {
        return cpcosto_prom_s_impuesto_fnd;
    }

    public void setCpcosto_prom_s_impuesto_fnd(String cpcosto_prom_s_impuesto_fnd) {
        this.cpcosto_prom_s_impuesto_fnd = cpcosto_prom_s_impuesto_fnd;
    }

    public String getCpcosto_prom_c_impuesto_fnd() {
        return cpcosto_prom_c_impuesto_fnd;
    }

    public void setCpcosto_prom_c_impuesto_fnd(String cpcosto_prom_c_impuesto_fnd) {
        this.cpcosto_prom_c_impuesto_fnd = cpcosto_prom_c_impuesto_fnd;
    }

    public String getCpcosto_anterior_c_impuesto_fnd() {
        return cpcosto_anterior_c_impuesto_fnd;
    }

    public void setCpcosto_anterior_c_impuesto_fnd(String cpcosto_anterior_c_impuesto_fnd) {
        this.cpcosto_anterior_c_impuesto_fnd = cpcosto_anterior_c_impuesto_fnd;
    }

    public String getCputilidad1_fnd() {
        return cputilidad1_fnd;
    }

    public void setCputilidad1_fnd(String cputilidad1_fnd) {
        this.cputilidad1_fnd = cputilidad1_fnd;
    }

    public String getCpprecio1_fnd() {
        return cpprecio1_fnd;
    }

    public void setCpprecio1_fnd(String cpprecio1_fnd) {
        this.cpprecio1_fnd = cpprecio1_fnd;
    }

    public String getCputilidad2_fnd() {
        return cputilidad2_fnd;
    }

    public void setCputilidad2_fnd(String cputilidad2_fnd) {
        this.cputilidad2_fnd = cputilidad2_fnd;
    }

    public String getCpprecio2_fnd() {
        return cpprecio2_fnd;
    }

    public void setCpprecio2_fnd(String cpprecio2_fnd) {
        this.cpprecio2_fnd = cpprecio2_fnd;
    }

    public String getCputildad3_fnd() {
        return cputildad3_fnd;
    }

    public void setCputildad3_fnd(String cputildad3_fnd) {
        this.cputildad3_fnd = cputildad3_fnd;
    }

    public String getCpprecio3_fnd() {
        return cpprecio3_fnd;
    }

    public void setCpprecio3_fnd(String cpprecio3_fnd) {
        this.cpprecio3_fnd = cpprecio3_fnd;
    }

    public String getCpoem_fnd() {
        return cpoem_fnd;
    }

    public void setCpoem_fnd(String cpoem_fnd) {
        this.cpoem_fnd = cpoem_fnd;
    }

    public String getCodigo_fnd() {
        return codigo_fnd;
    }

    public void setCodigo_fnd(String codigo_fnd) {
        this.codigo_fnd = codigo_fnd;
    }

    public String getPageProducto_fnd() {
        return pageProducto_fnd;
    }

    public void setPageProducto_fnd(String pageProducto_fnd) {
        this.pageProducto_fnd = pageProducto_fnd;
    }

    public Date getCpfecha_recepcion_fnd() {
        return cpfecha_recepcion_fnd;
    }

    public void setCpfecha_recepcion_fnd(Date cpfecha_recepcion_fnd) {
        this.cpfecha_recepcion_fnd = cpfecha_recepcion_fnd;
    }

    public Cproducto_fnd getSelectedProductoDTO_fnd() {
        return SelectedProductoDTO_fnd;
    }

    public void setSelectedProductoDTO_fnd(Cproducto_fnd SelectedProductoDTO_fnd) {
        this.SelectedProductoDTO_fnd = SelectedProductoDTO_fnd;
    }

    public List<Cproducto_fnd> getCProductoList_fnd() {
        return CProductoList_fnd;
    }

    public void setCProductoList_fnd(List<Cproducto_fnd> CProductoList_fnd) {
        this.CProductoList_fnd = CProductoList_fnd;
    }

    public List<Cmodelo> getCpcModeloListcbx_fnd() {
        return CpcModeloListcbx_fnd;
    }

    public void setCpcModeloListcbx_fnd(List<Cmodelo> CpcModeloListcbx_fnd) {
        this.CpcModeloListcbx_fnd = CpcModeloListcbx_fnd;
    }

    public List<Cmarca> getCpcModeloMarcaListcbx_fnd() {
        return CpcModeloMarcaListcbx_fnd;
    }

    public void setCpcModeloMarcaListcbx_fnd(List<Cmarca> CpcModeloMarcaListcbx_fnd) {
        this.CpcModeloMarcaListcbx_fnd = CpcModeloMarcaListcbx_fnd;
    }

    public List<Ccategoria> getCpccategoriaListcbx_fnd() {
        return CpccategoriaListcbx_fnd;
    }

    public void setCpccategoriaListcbx_fnd(List<Ccategoria> CpccategoriaListcbx_fnd) {
        this.CpccategoriaListcbx_fnd = CpccategoriaListcbx_fnd;
    }

    public void setCproveedorEdit(boolean CproveedorEdit) {
        this.CproveedorEdit = CproveedorEdit;
    }

    public String getCproveedorId() {
        return CproveedorId;
    }

    public String getCproveedordireccion() {
        return Cproveedordireccion;
    }

    public void setCproveedordireccion(String Cproveedordireccion) {
        this.Cproveedordireccion = Cproveedordireccion;
    }

    public String getCproveedortel1() {
        return Cproveedortel1;
    }

    public void setCproveedortel1(String Cproveedortel1) {
        this.Cproveedortel1 = Cproveedortel1;
    }

    public String getCproveedortel2() {
        return Cproveedortel2;
    }

    public void setCproveedortel2(String Cproveedortel2) {
        this.Cproveedortel2 = Cproveedortel2;
    }

    public String getCproveedormoto() {
        return Cproveedormoto;
    }

    public void setCproveedormoto(String Cproveedormoto) {
        this.Cproveedormoto = Cproveedormoto;
    }

    public void setCproveedorId(String CproveedorId) {
        this.CproveedorId = CproveedorId;
    }

    public String getCproveedorDescripcion() {
        return CproveedorDescripcion;
    }

    public void setCproveedorDescripcion(String CproveedorDescripcion) {
        this.CproveedorDescripcion = CproveedorDescripcion;
    }

    public String getCproveedorStatus() {
        return CproveedorStatus;
    }

    public void setCproveedorStatus(String CproveedorStatus) {
        this.CproveedorStatus = CproveedorStatus;
    }

    public Cproveedor getSelectedProveedorDTO() {
        return SelectedProveedorDTO;
    }

    public void setSelectedProveedorDTO(Cproveedor SelectedProveedorDTO) {
        this.SelectedProveedorDTO = SelectedProveedorDTO;
    }

    public List<Cproveedor> getCtipoProveedorList() {
        return CtipoProveedorList;
    }

    public void setCtipoProveedorList(List<Cproveedor> CtipoProveedorList) {
        this.CtipoProveedorList = CtipoProveedorList;
    }

    public String getPageCTipoProveedor() {
        return pageCTipoProveedor;
    }

    public void setPageCTipoProveedor(String pageCTipoProveedor) {
        this.pageCTipoProveedor = pageCTipoProveedor;
    }

    public boolean isCcategoriaEdit() {
        return CcategoriaEdit;
    }

    public void setCcategoriaEdit(boolean CcategoriaEdit) {
        this.CcategoriaEdit = CcategoriaEdit;
    }

    public String getCcategoriaId() {
        return CcategoriaId;
    }

    public void setCcategoriaId(String CcategoriaId) {
        this.CcategoriaId = CcategoriaId;
    }

    public String getCcategoriaDescripcion() {
        return CcategoriaDescripcion;
    }

    public void setCcategoriaDescripcion(String CcategoriaDescripcion) {
        this.CcategoriaDescripcion = CcategoriaDescripcion;
    }

    public String getCcategoriaStatus() {
        return CcategoriaStatus;
    }

    public void setCcategoriaStatus(String CcategoriaStatus) {
        this.CcategoriaStatus = CcategoriaStatus;
    }

    public Ccategoria getSelectedCategoriaDTO() {
        return SelectedCategoriaDTO;
    }

    public void setSelectedCategoriaDTO(Ccategoria SelectedCategoriaDTO) {
        this.SelectedCategoriaDTO = SelectedCategoriaDTO;
    }

    public List<Ccategoria> getCtipoCategoriaList() {
        return CtipoCategoriaList;
    }

    public void setCtipoCategoriaList(List<Ccategoria> CtipoCategoriaList) {
        this.CtipoCategoriaList = CtipoCategoriaList;
    }

    public String getPageCTipoCategoria() {
        return pageCTipoCategoria;
    }

    public void setPageCTipoCategoria(String pageCTipoCategoria) {
        this.pageCTipoCategoria = pageCTipoCategoria;
    }

    public String getCdocumentoDescripcion() {
        return CdocumentoDescripcion;
    }

    public Cproducto getSelectedProductoDTO() {
        return SelectedProductoDTO;
    }

    public void setSelectedProductoDTO(Cproducto SelectedProductoDTO) {
        this.SelectedProductoDTO = SelectedProductoDTO;
    }

    public List<Cproducto> getCProductoList() {
        return CProductoList;
    }

    public void setCProductoList(List<Cproducto> CProductoList) {
        this.CProductoList = CProductoList;
    }

    public String getCciudadmunicipioId() {
        return CciudadmunicipioId;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCpid_marca() {
        return cpid_marca;
    }

    public void setCpid_marca(String cpid_marca) {
        this.cpid_marca = cpid_marca;
    }

    public String getCpid_modelo() {
        return cpid_modelo;
    }

    public void setCpid_modelo(String cpid_modelo) {
        this.cpid_modelo = cpid_modelo;
    }

    public String getCpdescripcion() {
        return cpdescripcion;
    }

    public void setCpdescripcion(String cpdescripcion) {
        this.cpdescripcion = cpdescripcion;
    }

    public String getCpid_categoria() {
        return cpid_categoria;
    }

    public void setCpid_categoria(String cpid_categoria) {
        this.cpid_categoria = cpid_categoria;
    }

    public String getCpstock() {
        return cpstock;
    }

    public void setCpstock(String cpstock) {
        this.cpstock = cpstock;
    }

    public String getCpservicio() {
        return cpservicio;
    }

    public void setCpservicio(String cpservicio) {
        this.cpservicio = cpservicio;
    }

    public String getCpstock_minimo() {
        return cpstock_minimo;
    }

    public void setCpstock_minimo(String cpstock_minimo) {
        this.cpstock_minimo = cpstock_minimo;
    }

    public String getCpstock_maximo() {
        return cpstock_maximo;
    }

    public void setCpstock_maximo(String cpstock_maximo) {
        this.cpstock_maximo = cpstock_maximo;
    }

    public String getCpsuspendido() {
        return cpsuspendido;
    }

    public void setCpsuspendido(String cpsuspendido) {
        this.cpsuspendido = cpsuspendido;
    }

    public String getCpcosto_compra() {
        return cpcosto_compra;
    }

    public void setCpcosto_compra(String cpcosto_compra) {
        this.cpcosto_compra = cpcosto_compra;
    }

    public String getCpcosto_fob() {
        return cpcosto_fob;
    }

    public void setCpcosto_fob(String cpcosto_fob) {
        this.cpcosto_fob = cpcosto_fob;
    }

    public String getCpcosto_contable() {
        return cpcosto_contable;
    }

    public void setCpcosto_contable(String cpcosto_contable) {
        this.cpcosto_contable = cpcosto_contable;
    }

    public List<Cmodelo> getCpcModeloListcbx() {
        return CpcModeloListcbx;
    }

    public void setCpcModeloListcbx(List<Cmodelo> CpcModeloListcbx) {
        this.CpcModeloListcbx = CpcModeloListcbx;
    }

    public List<Cmarca> getCpcModeloMarcaListcbx() {
        return CpcModeloMarcaListcbx;
    }

    public void setCpcModeloMarcaListcbx(List<Cmarca> CpcModeloMarcaListcbx) {
        this.CpcModeloMarcaListcbx = CpcModeloMarcaListcbx;
    }

    public List<Ccategoria> getCpccategoriaListcbx() {
        return CpccategoriaListcbx;
    }

    public void setCpccategoriaListcbx(List<Ccategoria> CpccategoriaListcbx) {
        this.CpccategoriaListcbx = CpccategoriaListcbx;
    }

    public String getCpultimo_costo_s_impuesto() {
        return cpultimo_costo_s_impuesto;
    }

    public void setCpultimo_costo_s_impuesto(String cpultimo_costo_s_impuesto) {
        this.cpultimo_costo_s_impuesto = cpultimo_costo_s_impuesto;
    }

    public String getCpultimo_costo_c_impuesto() {
        return cpultimo_costo_c_impuesto;
    }

    public void setCpultimo_costo_c_impuesto(String cpultimo_costo_c_impuesto) {
        this.cpultimo_costo_c_impuesto = cpultimo_costo_c_impuesto;
    }

    public String getCpcosto_prom_s_impuesto() {
        return cpcosto_prom_s_impuesto;
    }

    public void setCpcosto_prom_s_impuesto(String cpcosto_prom_s_impuesto) {
        this.cpcosto_prom_s_impuesto = cpcosto_prom_s_impuesto;
    }

    public String getCpcosto_prom_c_impuesto() {
        return cpcosto_prom_c_impuesto;
    }

    public void setCpcosto_prom_c_impuesto(String cpcosto_prom_c_impuesto) {
        this.cpcosto_prom_c_impuesto = cpcosto_prom_c_impuesto;
    }

    public String getCpcosto_anterior_c_impuesto() {
        return cpcosto_anterior_c_impuesto;
    }

    public void setCpcosto_anterior_c_impuesto(String cpcosto_anterior_c_impuesto) {
        this.cpcosto_anterior_c_impuesto = cpcosto_anterior_c_impuesto;
    }

    public String getCputilidad1() {
        return cputilidad1;
    }

    public void setCputilidad1(String cputilidad1) {
        this.cputilidad1 = cputilidad1;
    }

    public String getCpprecio1() {
        return cpprecio1;
    }

    public void setCpprecio1(String cpprecio1) {
        this.cpprecio1 = cpprecio1;
    }

    public String getCputilidad2() {
        return cputilidad2;
    }

    public void setCputilidad2(String cputilidad2) {
        this.cputilidad2 = cputilidad2;
    }

    public String getCpprecio2() {
        return cpprecio2;
    }

    public void setCpprecio2(String cpprecio2) {
        this.cpprecio2 = cpprecio2;
    }

    public String getCputildad3() {
        return cputildad3;
    }

    public void setCputildad3(String cputildad3) {
        this.cputildad3 = cputildad3;
    }

    public String getCpprecio3() {
        return cpprecio3;
    }

    public void setCpprecio3(String cpprecio3) {
        this.cpprecio3 = cpprecio3;
    }

    public String getCpoem() {
        return cpoem;
    }

    public void setCpoem(String cpoem) {
        this.cpoem = cpoem;
    }

    public List<Cdepto> getSelectedClienteDeptoCbx() {
        return selectedClienteDeptoCbx;
    }

    public void setSelectedClienteDeptoCbx(List<Cdepto> selectedClienteDeptoCbx) {
        this.selectedClienteDeptoCbx = selectedClienteDeptoCbx;
    }

    public void setCciudadmunicipioId(String CciudadmunicipioId) {
        this.CciudadmunicipioId = CciudadmunicipioId;
    }

    public String getCciudaddeptoId() {
        return CciudaddeptoId;
    }

    public void setCciudaddeptoId(String CciudaddeptoId) {
        this.CciudaddeptoId = CciudaddeptoId;
    }

    public List<Cmunicipio> getSelectedClienteDeptoMunicipioCbx() {
        return selectedClienteDeptoMunicipioCbx;
    }

    public void setSelectedClienteDeptoMunicipioCbx(List<Cmunicipio> selectedClienteDeptoMunicipioCbx) {
        this.selectedClienteDeptoMunicipioCbx = selectedClienteDeptoMunicipioCbx;
    }

    public List<Cciudad> getSelectedClienteCiudadCbx() {
        return selectedClienteCiudadCbx;
    }

    public void setSelectedClienteCiudadCbx(List<Cciudad> selectedClienteCiudadCbx) {
        this.selectedClienteCiudadCbx = selectedClienteCiudadCbx;
    }

    public boolean isCciudadEdit() {
        return CciudadEdit;
    }

    public List<Cdepto> getSelectedCiudadDeptoCbx() {
        return selectedCiudadDeptoCbx;
    }

    public void setSelectedCiudadDeptoCbx(List<Cdepto> selectedCiudadDeptoCbx) {
        this.selectedCiudadDeptoCbx = selectedCiudadDeptoCbx;
    }

    public void setCciudadEdit(boolean CciudadEdit) {
        this.CciudadEdit = CciudadEdit;
    }

    public String getCciudadId() {
        return CciudadId;
    }

    public boolean isCclienteEdit() {
        return CclienteEdit;
    }

    public void setCclienteEdit(boolean CclienteEdit) {
        this.CclienteEdit = CclienteEdit;
    }

    public String getCclienteId() {
        return CclienteId;
    }

    public void setCclienteId(String CclienteId) {
        this.CclienteId = CclienteId;
    }

    public String getCclientenombre() {
        return Cclientenombre;
    }

    public void setCclientenombre(String Cclientenombre) {
        this.Cclientenombre = Cclientenombre;
    }

    public String getCclientedireccion() {
        return Cclientedireccion;
    }

    public void setCclientedireccion(String Cclientedireccion) {
        this.Cclientedireccion = Cclientedireccion;
    }

    public String getCclienteidDepto() {
        return CclienteidDepto;
    }

    public void setCclienteidDepto(String CclienteidDepto) {
        this.CclienteidDepto = CclienteidDepto;
    }

    public String getCclienteidMunicipio() {
        return CclienteidMunicipio;
    }

    public void setCclienteidMunicipio(String CclienteidMunicipio) {
        this.CclienteidMunicipio = CclienteidMunicipio;
    }

    public String getCclienteidCiudad() {
        return CclienteidCiudad;
    }

    public void setCclienteidCiudad(String CclienteidCiudad) {
        this.CclienteidCiudad = CclienteidCiudad;
    }

    public String getCclienteregistroFiscal() {
        return CclienteregistroFiscal;
    }

    public void setCclienteregistroFiscal(String CclienteregistroFiscal) {
        this.CclienteregistroFiscal = CclienteregistroFiscal;
    }

    public String getCclientenit() {
        return Cclientenit;
    }

    public void setCclientenit(String Cclientenit) {
        this.Cclientenit = Cclientenit;
    }

    public String getCclienteGiro() {
        return CclienteGiro;
    }

    public void setCclienteGiro(String CclienteGiro) {
        this.CclienteGiro = CclienteGiro;
    }

    public String getCclientetelefono1() {
        return Cclientetelefono1;
    }

    public void setCclientetelefono1(String Cclientetelefono1) {
        this.Cclientetelefono1 = Cclientetelefono1;
    }

    public String getCclientetelefono2() {
        return Cclientetelefono2;
    }

    public void setCclientetelefono2(String Cclientetelefono2) {
        this.Cclientetelefono2 = Cclientetelefono2;
    }

    public String getCclientefax() {
        return Cclientefax;
    }

    public void setCclientefax(String Cclientefax) {
        this.Cclientefax = Cclientefax;
    }

    public String getCclientelimiteDeCredito() {
        return CclientelimiteDeCredito;
    }

    public void setCclientelimiteDeCredito(String CclientelimiteDeCredito) {
        this.CclientelimiteDeCredito = CclientelimiteDeCredito;
    }

    public String getCclienteemail() {
        return Cclienteemail;
    }

    public void setCclienteemail(String Cclienteemail) {
        this.Cclienteemail = Cclienteemail;
    }

    public String getCclientecomentarios() {
        return Cclientecomentarios;
    }

    public void setCclientecomentarios(String Cclientecomentarios) {
        this.Cclientecomentarios = Cclientecomentarios;
    }

    public String getCclientepercepcion() {
        return Cclientepercepcion;
    }

    public void setCclientepercepcion(String Cclientepercepcion) {
        this.Cclientepercepcion = Cclientepercepcion;
    }

    public Ccliente getSelectedClienteDTO() {
        return SelectedClienteDTO;
    }

    public void setSelectedClienteDTO(Ccliente SelectedClienteDTO) {
        this.SelectedClienteDTO = SelectedClienteDTO;
    }

    public List<Ccliente> getCtipoClienteList() {
        return CtipoClienteList;
    }

    public void setCtipoClienteList(List<Ccliente> CtipoClienteList) {
        this.CtipoClienteList = CtipoClienteList;
    }

    public String getPageCTipoCliente() {
        return pageCTipoCliente;
    }

    public void setPageCTipoCliente(String pageCTipoCliente) {
        this.pageCTipoCliente = pageCTipoCliente;
    }

    public void setCciudadId(String CciudadId) {
        this.CciudadId = CciudadId;
    }

    public String getCciudadDescripcion() {
        return CciudadDescripcion;
    }

    public void setCciudadDescripcion(String CciudadDescripcion) {
        this.CciudadDescripcion = CciudadDescripcion;
    }

    public String getCciudadStatus() {
        return CciudadStatus;
    }

    public void setCciudadStatus(String CciudadStatus) {
        this.CciudadStatus = CciudadStatus;
    }

    public Cciudad getSelectedCiudadDTO() {
        return SelectedCiudadDTO;
    }

    public void setSelectedCiudadDTO(Cciudad SelectedCiudadDTO) {
        this.SelectedCiudadDTO = SelectedCiudadDTO;
    }

    public List<Cciudad> getCtipoCiudadList() {
        return CtipoCiudadList;
    }

    public void setCtipoCiudadList(List<Cciudad> CtipoCiudadList) {
        this.CtipoCiudadList = CtipoCiudadList;
    }

    public String getPageCTipoCiudad() {
        return pageCTipoCiudad;
    }

    public boolean isCempresaEdit() {
        return CempresaEdit;
    }

    public void setCempresaEdit(boolean CempresaEdit) {
        this.CempresaEdit = CempresaEdit;
    }

    public String getCempresaId() {
        return CempresaId;
    }

    public void setCempresaId(String CempresaId) {
        this.CempresaId = CempresaId;
    }

    public String getCempresaDescripcion() {
        return CempresaDescripcion;
    }

    public void setCempresaDescripcion(String CempresaDescripcion) {
        this.CempresaDescripcion = CempresaDescripcion;
    }

    public String getCempresaStatus() {
        return CempresaStatus;
    }

    public void setCempresaStatus(String CempresaStatus) {
        this.CempresaStatus = CempresaStatus;
    }

    public Cempresa getSelectedCempresaDTO() {
        return SelectedCempresaDTO;
    }

    public void setSelectedCempresaDTO(Cempresa SelectedCempresaDTO) {
        this.SelectedCempresaDTO = SelectedCempresaDTO;
    }

    public List<Cempresa> getCtipoCempresaList() {
        return CtipoCempresaList;
    }

    public void setCtipoCempresaList(List<Cempresa> CtipoCempresaList) {
        this.CtipoCempresaList = CtipoCempresaList;
    }

    public String getPageCTipoCempresa() {
        return pageCTipoCempresa;
    }

    public void setPageCTipoCempresa(String pageCTipoCempresa) {
        this.pageCTipoCempresa = pageCTipoCempresa;
    }

    public void setPageCTipoCiudad(String pageCTipoCiudad) {
        this.pageCTipoCiudad = pageCTipoCiudad;
    }

    public boolean isCdeptoEdit() {
        return CdeptoEdit;
    }

    public boolean isCmunicipioEdit() {
        return CmunicipioEdit;
    }

    public void setCmunicipioEdit(boolean CmunicipioEdit) {
        this.CmunicipioEdit = CmunicipioEdit;
    }

    public List<Cproducto_fnd> getCProductoList_fnd_filter() {
        return CProductoList_fnd_filter;
    }

    public void setCProductoList_fnd_filter(List<Cproducto_fnd> CProductoList_fnd_filter) {
        this.CProductoList_fnd_filter = CProductoList_fnd_filter;
    }

    public String getCmunicipioId() {
        return CmunicipioId;
    }

    public void setCmunicipioId(String CmunicipioId) {
        this.CmunicipioId = CmunicipioId;
    }

    public String getCmunicipioDescripcion() {
        return CmunicipioDescripcion;
    }

    public void setCmunicipioDescripcion(String CmunicipioDescripcion) {
        this.CmunicipioDescripcion = CmunicipioDescripcion;
    }

    public String getCmunicipioStatus() {
        return CmunicipioStatus;
    }

    public void setCmunicipioStatus(String CmunicipioStatus) {
        this.CmunicipioStatus = CmunicipioStatus;
    }

    public String getCmunicipioDescripcionDepto() {
        return CmunicipioDescripcionDepto;
    }

    public void setCmunicipioDescripcionDepto(String CmunicipioDescripcionDepto) {
        this.CmunicipioDescripcionDepto = CmunicipioDescripcionDepto;
    }

    public String getCmunicipioIdDepto() {
        return CmunicipioIdDepto;
    }

    public void setCmunicipioIdDepto(String CmunicipioIdDepto) {
        this.CmunicipioIdDepto = CmunicipioIdDepto;
    }

    public Cmunicipio getSelectedMunicipioDTO() {
        return SelectedMunicipioDTO;
    }

    public void setSelectedMunicipioDTO(Cmunicipio SelectedMunicipioDTO) {
        this.SelectedMunicipioDTO = SelectedMunicipioDTO;
    }

    public List<Cmunicipio> getCtipoMunicipioList() {
        return CtipoMunicipioList;
    }

    public void setCtipoMunicipioList(List<Cmunicipio> CtipoMunicipioList) {
        this.CtipoMunicipioList = CtipoMunicipioList;
    }

    public List<Cdepto> getcMunicipioDeptoListcbx() {
        return cMunicipioDeptoListcbx;
    }

    public void setcMunicipioDeptoListcbx(List<Cdepto> cMunicipioDeptoListcbx) {
        this.cMunicipioDeptoListcbx = cMunicipioDeptoListcbx;
    }

    public List<Cdepto> getCmunicipiodeptolist() {
        return cmunicipiodeptolist;
    }

    public void setCmunicipiodeptolist(List<Cdepto> cmunicipiodeptolist) {
        this.cmunicipiodeptolist = cmunicipiodeptolist;
    }

    public String getPageCTipoMunicipio() {
        return pageCTipoMunicipio;
    }

    public void setPageCTipoMunicipio(String pageCTipoMunicipio) {
        this.pageCTipoMunicipio = pageCTipoMunicipio;
    }

    public String getSelectedMunicipioDeptoCbx() {
        return selectedMunicipioDeptoCbx;
    }

    public void setSelectedMunicipioDeptoCbx(String selectedMunicipioDeptoCbx) {
        this.selectedMunicipioDeptoCbx = selectedMunicipioDeptoCbx;
    }

    public void setCdeptoEdit(boolean CdeptoEdit) {
        this.CdeptoEdit = CdeptoEdit;
    }

    public String getCdeptoId() {
        return CdeptoId;
    }

    public void setCdeptoId(String CdeptoId) {
        this.CdeptoId = CdeptoId;
    }

    public String getCdeptoDescripcion() {
        return CdeptoDescripcion;
    }

    public void setCdeptoDescripcion(String CdeptoDescripcion) {
        this.CdeptoDescripcion = CdeptoDescripcion;
    }

    public String getCdeptoStatus() {
        return CdeptoStatus;
    }

    public void setCdeptoStatus(String CdeptoStatus) {
        this.CdeptoStatus = CdeptoStatus;
    }

    public Cdepto getSelectedDeptoDTO() {
        return SelectedDeptoDTO;
    }

    public void setSelectedDeptoDTO(Cdepto SelectedDeptoDTO) {
        this.SelectedDeptoDTO = SelectedDeptoDTO;
    }

    public List<Cdepto> getCtipoDeptoList() {
        return CtipoDeptoList;
    }

    public void setCtipoDeptoList(List<Cdepto> CtipoDeptoList) {
        this.CtipoDeptoList = CtipoDeptoList;
    }

    public String getPageCTipoDepto() {
        return pageCTipoDepto;
    }

    public void setPageCTipoDepto(String pageCTipoDepto) {
        this.pageCTipoDepto = pageCTipoDepto;
    }

    public boolean isCmodeloEdit() {
        return CmodeloEdit;
    }

    public boolean isCgiroEdit() {
        return CgiroEdit;
    }

    public void setCgiroEdit(boolean CgiroEdit) {
        this.CgiroEdit = CgiroEdit;
    }

    public String getCgiroId() {
        return CgiroId;
    }

    public void setCgiroId(String CgiroId) {
        this.CgiroId = CgiroId;
    }

    public String getCgiroDescripcion() {
        return CgiroDescripcion;
    }

    public void setCgiroDescripcion(String CgiroDescripcion) {
        this.CgiroDescripcion = CgiroDescripcion;
    }

    public String getCgiroStatus() {
        return CgiroStatus;
    }

    public void setCgiroStatus(String CgiroStatus) {
        this.CgiroStatus = CgiroStatus;
    }

    public Cgiro getSelectedGiroDTO() {
        return SelectedGiroDTO;
    }

    public void setSelectedGiroDTO(Cgiro SelectedGiroDTO) {
        this.SelectedGiroDTO = SelectedGiroDTO;
    }

    public List<Cgiro> getCtipoGiroList() {
        return CtipoGiroList;
    }

    public void setCtipoGiroList(List<Cgiro> CtipoGiroList) {
        this.CtipoGiroList = CtipoGiroList;
    }

    public String getPageCTipoGiro() {
        return pageCTipoGiro;
    }

    public void setPageCTipoGiro(String pageCTipoGiro) {
        this.pageCTipoGiro = pageCTipoGiro;
    }

    public void setCmodeloEdit(boolean CmodeloEdit) {
        this.CmodeloEdit = CmodeloEdit;
    }

    public String getCmodeloId() {
        return CmodeloId;
    }

    public void setCmodeloId(String CmodeloId) {
        this.CmodeloId = CmodeloId;
    }

    public String getCmodeloDescripcion() {
        return CmodeloDescripcion;
    }

    public void setCmodeloDescripcion(String CmodeloDescripcion) {
        this.CmodeloDescripcion = CmodeloDescripcion;
    }

    public String getCmodeloStatus() {
        return CmodeloStatus;
    }

    public void setCmodeloStatus(String CmodeloStatus) {
        this.CmodeloStatus = CmodeloStatus;
    }

    public String getCmodeloDescripcionMarca() {
        return CmodeloDescripcionMarca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCmodeloDescripcionMarca(String CmodeloDescripcionMarca) {
        this.CmodeloDescripcionMarca = CmodeloDescripcionMarca;
    }

    public String getCmodeloIdMarca() {
        return CmodeloIdMarca;
    }

    public void setCmodeloIdMarca(String CmodeloIdMarca) {
        this.CmodeloIdMarca = CmodeloIdMarca;
    }

    public Cmodelo getSelectedModeloDTO() {
        return SelectedModeloDTO;
    }

    public void setSelectedModeloDTO(Cmodelo SelectedModeloDTO) {
        this.SelectedModeloDTO = SelectedModeloDTO;
    }

    public List<Cmodelo> getCtipoModeloList() {
        return CtipoModeloList;
    }

    public void setCtipoModeloList(List<Cmodelo> CtipoModeloList) {
        this.CtipoModeloList = CtipoModeloList;
    }

    public String getPageCTipoModelo() {
        return pageCTipoModelo;
    }

    public void setPageCTipoModelo(String pageCTipoModelo) {
        this.pageCTipoModelo = pageCTipoModelo;
    }

    public void setCdocumentoDescripcion(String CdocumentoDescripcion) {
        this.CdocumentoDescripcion = CdocumentoDescripcion;
    }

    public List<CtipoDocumento> getCtipoDocumentoList() {
        return CtipoDocumentoList;
    }

    public boolean isCprodEdit() {
        return CprodEdit;
    }

    public void setCprodEdit(boolean CprodEdit) {
        this.CprodEdit = CprodEdit;
    }

    public String getCprodId() {
        return CprodId;
    }

    public void setCprodId(String CprodId) {
        this.CprodId = CprodId;
    }

    public String getCprodDescripcion() {
        return CprodDescripcion;
    }

    public void setCprodDescripcion(String CprodDescripcion) {
        this.CprodDescripcion = CprodDescripcion;
    }

    public String getCprodStatus() {
        return CprodStatus;
    }

    public void setCprodStatus(String CprodStatus) {
        this.CprodStatus = CprodStatus;
    }

    public CtipoProducto getSelectedProdDTO() {
        return SelectedProdDTO;
    }

    public void setSelectedProdDTO(CtipoProducto SelectedProdDTO) {
        this.SelectedProdDTO = SelectedProdDTO;
    }

    public List<CtipoProducto> getCtipoProdList() {
        return CtipoProdList;
    }

    public void setCtipoProdList(List<CtipoProducto> CtipoProdList) {
        this.CtipoProdList = CtipoProdList;
    }

    public boolean isCalmacenEdit() {
        return CalmacenEdit;
    }

    public void setCalmacenEdit(boolean CalmacenEdit) {
        this.CalmacenEdit = CalmacenEdit;
    }

    public String getCalmacenId() {
        return CalmacenId;
    }

    public void setCalmacenId(String CalmacenId) {
        this.CalmacenId = CalmacenId;
    }

    public String getCalmacenDescripcion() {
        return CalmacenDescripcion;
    }

    public void setCalmacenDescripcion(String CalmacenDescripcion) {
        this.CalmacenDescripcion = CalmacenDescripcion;
    }

    public String getCalmacenStatus() {
        return CalmacenStatus;
    }

    public void setCalmacenStatus(String CalmacenStatus) {
        this.CalmacenStatus = CalmacenStatus;
    }

    public Calmacen getSelectedAlmacenDTO() {
        return SelectedAlmacenDTO;
    }

    public void setSelectedAlmacenDTO(Calmacen SelectedAlmacenDTO) {
        this.SelectedAlmacenDTO = SelectedAlmacenDTO;
    }

    public List<Calmacen> getCtipoAlmacenList() {
        return CtipoAlmacenList;
    }

    public void setCtipoAlmacenList(List<Calmacen> CtipoAlmacenList) {
        this.CtipoAlmacenList = CtipoAlmacenList;
    }

    public String getPageCTipoAlmacen() {
        return pageCTipoAlmacen;
    }

    public void setPageCTipoAlmacen(String pageCTipoAlmacen) {
        this.pageCTipoAlmacen = pageCTipoAlmacen;
    }

    public String getPageCTipoProd() {
        return pageCTipoProd;
    }

    public void setPageCTipoProd(String pageCTipoProd) {
        this.pageCTipoProd = pageCTipoProd;
    }

    public void setCtipoDocumentoList(List<CtipoDocumento> CtipoDocumentoList) {
        this.CtipoDocumentoList = CtipoDocumentoList;
    }

    public String getCvehiculoId() {
        return CvehiculoId;
    }

    public void setCvehiculoId(String CvehiculoId) {
        this.CvehiculoId = CvehiculoId;
    }

    public String getCvehiculoDescripcion() {
        return CvehiculoDescripcion;
    }

    public void setCvehiculoDescripcion(String CvehiculoDescripcion) {
        this.CvehiculoDescripcion = CvehiculoDescripcion;
    }

    public String getCvehiculoStatus() {
        return CvehiculoStatus;
    }

    public boolean isCdocumentoEdit() {
        return CdocumentoEdit;
    }

    public void setCdocumentoEdit(boolean CdocumentoEdit) {
        this.CdocumentoEdit = CdocumentoEdit;
    }

    public String getCdocumentoId() {
        return CdocumentoId;
    }

    public String getCalmacenDireccion1() {
        return CalmacenDireccion1;
    }

    public void setCalmacenDireccion1(String CalmacenDireccion1) {
        this.CalmacenDireccion1 = CalmacenDireccion1;
    }

    public String getCalmacenDireccion2() {
        return CalmacenDireccion2;
    }

    public void setCalmacenDireccion2(String CalmacenDireccion2) {
        this.CalmacenDireccion2 = CalmacenDireccion2;
    }

    public String getCalmacenTel1() {
        return CalmacenTel1;
    }

    public void setCalmacenTel1(String CalmacenTel1) {
        this.CalmacenTel1 = CalmacenTel1;
    }

    public String getCalmacenTel2() {
        return CalmacenTel2;
    }

    public void setCalmacenTel2(String CalmacenTel2) {
        this.CalmacenTel2 = CalmacenTel2;
    }

    public String getCalmacenTransferible() {
        return CalmacenTransferible;
    }

    public void setCalmacenTransferible(String CalmacenTransferible) {
        this.CalmacenTransferible = CalmacenTransferible;
    }

    public void setCdocumentoId(String CdocumentoId) {
        this.CdocumentoId = CdocumentoId;
    }

    public String getCdocumentoStatus() {
        return CdocumentoStatus;
    }

    public String getPagenotaCTipoClienteSearch() {
        return pagenotaCTipoClienteSearch;
    }

    public void setPagenotaCTipoClienteSearch(String pagenotaCTipoClienteSearch) {
        this.pagenotaCTipoClienteSearch = pagenotaCTipoClienteSearch;
    }

    public String getPagenotaProductoSearch() {
        return pagenotaProductoSearch;
    }

    public void setPagenotaProductoSearch(String pagenotaProductoSearch) {
        this.pagenotaProductoSearch = pagenotaProductoSearch;
    }

    public List<Cmarca> getCmodelomarcalist() {
        return cmodelomarcalist;
    }

    public void setCmodelomarcalist(List<Cmarca> cmodelomarcalist) {
        this.cmodelomarcalist = cmodelomarcalist;
    }

    public void setCdocumentoStatus(String CdocumentoStatus) {
        this.CdocumentoStatus = CdocumentoStatus;
    }

    public CtipoDocumento getSelectedDocumentoDTO() {
        return SelectedDocumentoDTO;
    }

    public void setSelectedDocumentoDTO(CtipoDocumento SelectedDocumentoDTO) {
        this.SelectedDocumentoDTO = SelectedDocumentoDTO;
    }

    public List<CtipoVehiculo> getCtipoVehiculoList() {
        return CtipoVehiculoList;
    }

    public void setCtipoVehiculoList(List<CtipoVehiculo> CtipoVehiculoList) {
        this.CtipoVehiculoList = CtipoVehiculoList;
    }

    public String getPageCTipoDocumento() {
        return pageCTipoDocumento;
    }

    public void setPageCTipoDocumento(String pageCTipoDocumento) {
        this.pageCTipoDocumento = pageCTipoDocumento;
    }

    public void setCvehiculoStatus(String CvehiculoStatus) {
        this.CvehiculoStatus = CvehiculoStatus;
    }

    public CtipoVehiculo getSelectedVehiculoDTO() {
        return SelectedVehiculoDTO;
    }

    public void setSelectedVehiculoDTO(CtipoVehiculo SelectedVehiculoDTO) {
        this.SelectedVehiculoDTO = SelectedVehiculoDTO;
    }

    public String getPageCTipoVehiculo() {
        return pageCTipoVehiculo;
    }

    public void setPageCTipoVehiculo(String pageCTipoVehiculo) {
        this.pageCTipoVehiculo = pageCTipoVehiculo;
    }

    public boolean isCvehiculoEdit() {
        return CvehiculoEdit;
    }

    public void setCvehiculoEdit(boolean CvehiculoEdit) {
        this.CvehiculoEdit = CvehiculoEdit;
    }

    public boolean isCdpEdit() {
        return CdpEdit;
    }

    public void setCdpEdit(boolean CdpEdit) {
        this.CdpEdit = CdpEdit;
    }

    public Date getCpfecha_recepcion() {
        return cpfecha_recepcion;
    }

    public void setCpfecha_recepcion(Date cpfecha_recepcion) {
        this.cpfecha_recepcion = cpfecha_recepcion;
    }

    public String getCdpId() {
        return CdpId;
    }

    public void setCdpId(String CdpId) {
        this.CdpId = CdpId;
    }

    public String getCdpDescripcion() {
        return CdpDescripcion;
    }

    public List<Cmarca> getcModeloMarcaListcbx() {
        return cModeloMarcaListcbx;
    }

    public void setcModeloMarcaListcbx(List<Cmarca> cModeloMarcaListcbx) {
        this.cModeloMarcaListcbx = cModeloMarcaListcbx;
    }

    public boolean isCtallerEdit() {
        return CtallerEdit;
    }

    public void setCtallerEdit(boolean CtallerEdit) {
        this.CtallerEdit = CtallerEdit;
    }

    public String getCtallerId() {
        return CtallerId;
    }

    public void setCtallerId(String CtallerId) {
        this.CtallerId = CtallerId;
    }

    public String getCtallerDescripcion() {
        return CtallerDescripcion;
    }

    public String getPageFacordenEntrega() {
        return pageFacordenEntrega;
    }

    public void setPageFacordenEntrega(String pageFacordenEntrega) {
        this.pageFacordenEntrega = pageFacordenEntrega;
    }

    public void setCtallerDescripcion(String CtallerDescripcion) {
        this.CtallerDescripcion = CtallerDescripcion;
    }

    public String getCtallerStatus() {
        return CtallerStatus;
    }

    public boolean isCempleadoEdit() {
        return CempleadoEdit;
    }

    public void setCempleadoEdit(boolean CempleadoEdit) {
        this.CempleadoEdit = CempleadoEdit;
    }

    public String getCempleadoId() {
        return CempleadoId;
    }

    public void setCempleadoId(String CempleadoId) {
        this.CempleadoId = CempleadoId;
    }

    public String getCempleadoDescripcion() {
        return CempleadoDescripcion;
    }

    public void setCempleadoDescripcion(String CempleadoDescripcion) {
        this.CempleadoDescripcion = CempleadoDescripcion;
    }

    public String getCempleadoStatus() {
        return CempleadoStatus;
    }

    public boolean isCfpagoEdit() {
        return CfpagoEdit;
    }

    public void setCfpagoEdit(boolean CfpagoEdit) {
        this.CfpagoEdit = CfpagoEdit;
    }

    public String getCfpagoId() {
        return CfpagoId;
    }

    public void setCfpagoId(String CfpagoId) {
        this.CfpagoId = CfpagoId;
    }

    public String getCfpagoDescripcion() {
        return CfpagoDescripcion;
    }

    public void setCfpagoDescripcion(String CfpagoDescripcion) {
        this.CfpagoDescripcion = CfpagoDescripcion;
    }

    public String getCfpagoStatus() {
        return CfpagoStatus;
    }

    public void setCfpagoStatus(String CfpagoStatus) {
        this.CfpagoStatus = CfpagoStatus;
    }

    public Cfpago getSelectedFpagoDTO() {
        return SelectedFpagoDTO;
    }

    public void setSelectedFpagoDTO(Cfpago SelectedFpagoDTO) {
        this.SelectedFpagoDTO = SelectedFpagoDTO;
    }

    public List<Cfpago> getCtipoFpagoList() {
        return CtipoFpagoList;
    }

    public void setCtipoFpagoList(List<Cfpago> CtipoFpagoList) {
        this.CtipoFpagoList = CtipoFpagoList;
    }

    public String getPageCTipoFpago() {
        return pageCTipoFpago;
    }

    public void setPageCTipoFpago(String pageCTipoFpago) {
        this.pageCTipoFpago = pageCTipoFpago;
    }

    public void setCempleadoStatus(String CempleadoStatus) {
        this.CempleadoStatus = CempleadoStatus;
    }

    public Cempleado getSelectedEmpleadoDTO() {
        return SelectedEmpleadoDTO;
    }

    public void setSelectedEmpleadoDTO(Cempleado SelectedEmpleadoDTO) {
        this.SelectedEmpleadoDTO = SelectedEmpleadoDTO;
    }

    public List<Cempleado> getCtipoEmpleadoList() {
        return CtipoEmpleadoList;
    }

    public void setCtipoEmpleadoList(List<Cempleado> CtipoEmpleadoList) {
        this.CtipoEmpleadoList = CtipoEmpleadoList;
    }

    public String getPageCTipoEmpleado() {
        return pageCTipoEmpleado;
    }

    public void setPageCTipoEmpleado(String pageCTipoEmpleado) {
        this.pageCTipoEmpleado = pageCTipoEmpleado;
    }

    public void setCtallerStatus(String CtallerStatus) {
        this.CtallerStatus = CtallerStatus;
    }

    public Ctaller getSelectedTallerDTO() {
        return SelectedTallerDTO;
    }

    public void setSelectedTallerDTO(Ctaller SelectedTallerDTO) {
        this.SelectedTallerDTO = SelectedTallerDTO;
    }

    public List<Ctaller> getCtipoTallerList() {
        return CtipoTallerList;
    }

    public void setCtipoTallerList(List<Ctaller> CtipoTallerList) {
        this.CtipoTallerList = CtipoTallerList;
    }

    public String getPageCTipoTaller() {
        return pageCTipoTaller;
    }

    public boolean isCestiloEdit() {
        return CestiloEdit;
    }

    public void setCestiloEdit(boolean CestiloEdit) {
        this.CestiloEdit = CestiloEdit;
    }

    public String getCestiloId() {
        return CestiloId;
    }

    public void setCestiloId(String CestiloId) {
        this.CestiloId = CestiloId;
    }

    public String getCestiloDescripcion() {
        return CestiloDescripcion;
    }

    public void setCestiloDescripcion(String CestiloDescripcion) {
        this.CestiloDescripcion = CestiloDescripcion;
    }

    public String getCestiloStatus() {
        return CestiloStatus;
    }

    public void setCestiloStatus(String CestiloStatus) {
        this.CestiloStatus = CestiloStatus;
    }

    public Cestilo getSelectedEstiloDTO() {
        return SelectedEstiloDTO;
    }

    public void setSelectedEstiloDTO(Cestilo SelectedEstiloDTO) {
        this.SelectedEstiloDTO = SelectedEstiloDTO;
    }

    public List<Cestilo> getCtipoEstiloList() {
        return CtipoEstiloList;
    }

    public void setCtipoEstiloList(List<Cestilo> CtipoEstiloList) {
        this.CtipoEstiloList = CtipoEstiloList;
    }

    public String getPageCTipoEstilo() {
        return pageCTipoEstilo;
    }

    public void setPageCTipoEstilo(String pageCTipoEstilo) {
        this.pageCTipoEstilo = pageCTipoEstilo;
    }

    public void setPageCTipoTaller(String pageCTipoTaller) {
        this.pageCTipoTaller = pageCTipoTaller;
    }

    public void setCdpDescripcion(String CdpDescripcion) {
        this.CdpDescripcion = CdpDescripcion;
    }

    public String getCdpStatus() {
        return CdpStatus;
    }

    public void setCdpStatus(String CdpStatus) {
        this.CdpStatus = CdpStatus;
    }

    public CtipoDp getSelectedDpDTO() {
        return SelectedDpDTO;
    }

    public void setSelectedDpDTO(CtipoDp SelectedDpDTO) {
        this.SelectedDpDTO = SelectedDpDTO;
    }

    public List<CtipoDp> getCtipoDpList() {
        return CtipoDpList;
    }

    public void setCtipoDpList(List<CtipoDp> CtipoDpList) {
        this.CtipoDpList = CtipoDpList;
    }

    public String getPageCTipoDp() {
        return pageCTipoDp;
    }

    public void setPageCTipoDp(String pageCTipoDp) {
        this.pageCTipoDp = pageCTipoDp;
    }

    public boolean isCmarcaEdit() {
        return CmarcaEdit;
    }

    public void setCmarcaEdit(boolean CmarcaEdit) {
        this.CmarcaEdit = CmarcaEdit;
    }

    public String getCmarcaId() {
        return CmarcaId;
    }

    public void setCmarcaId(String CmarcaId) {
        this.CmarcaId = CmarcaId;
    }

    public String getCmarcaDescripcion() {
        return CmarcaDescripcion;
    }

    public void setCmarcaDescripcion(String CmarcaDescripcion) {
        this.CmarcaDescripcion = CmarcaDescripcion;
    }

    public String getCmarcaStatus() {
        return CmarcaStatus;
    }

    public void setCmarcaStatus(String CmarcaStatus) {
        this.CmarcaStatus = CmarcaStatus;
    }

    public Cmarca getSelectedMarcaDTO() {
        return SelectedMarcaDTO;
    }

    public void setSelectedMarcaDTO(Cmarca SelectedMarcaDTO) {
        this.SelectedMarcaDTO = SelectedMarcaDTO;
    }

    public List<Cmarca> getCtipoMarcaList() {
        return CtipoMarcaList;
    }

    public void setCtipoMarcaList(List<Cmarca> CtipoMarcaList) {
        this.CtipoMarcaList = CtipoMarcaList;
    }

    public String getPageCTipoMarca() {
        return pageCTipoMarca;
    }

    public void setPageCTipoMarca(String pageCTipoMarca) {
        this.pageCTipoMarca = pageCTipoMarca;
    }

    public String getSelectedModeloMarcaCbx() {
        return selectedModeloMarcaCbx;
    }

    public List<Cmunicipio> getSelectedCiudadDeptoMunicipioCbx() {
        return selectedCiudadDeptoMunicipioCbx;
    }

    public void setSelectedCiudadDeptoMunicipioCbx(List<Cmunicipio> selectedCiudadDeptoMunicipioCbx) {
        this.selectedCiudadDeptoMunicipioCbx = selectedCiudadDeptoMunicipioCbx;
    }

    public void setSelectedModeloMarcaCbx(String selectedModeloMarcaCbx) {
        this.selectedModeloMarcaCbx = selectedModeloMarcaCbx;
    }

    public String getHttpParamHost() {
        return httpParamHost;
    }

    public String getPageProducto() {
        return pageProducto;
    }

    public void setPageProducto(String pageProducto) {
        this.pageProducto = pageProducto;
    }

    public void setHttpParamHost(String httpParamHost) {
        this.httpParamHost = httpParamHost;
    }

    public String getPageDetalleArticulos() {
        return pageDetalleArticulos;
    }

    public void setPageDetalleArticulos(String pageDetalleArticulos) {
        this.pageDetalleArticulos = pageDetalleArticulos;
    }

    public String getPageOrdenEntrega() {
        return pageOrdenEntrega;
    }

    public void setPageOrdenEntrega(String pageOrdenEntrega) {
        this.pageOrdenEntrega = pageOrdenEntrega;
    }

    public String getEmpleadoCheck() {
        return empleadoCheck;
    }

    public void setEmpleadoCheck(String empleadoCheck) {
        this.empleadoCheck = empleadoCheck;
    }

    public String getSucursalCheck() {
        return sucursalCheck;
    }

    public void setSucursalCheck(String sucursalCheck) {
        this.sucursalCheck = sucursalCheck;
    }

    public String getPageCTipoClienteSearch() {
        return pageCTipoClienteSearch;
    }

    public void setPageCTipoClienteSearch(String pageCTipoClienteSearch) {
        this.pageCTipoClienteSearch = pageCTipoClienteSearch;
    }

    public String getPageProductoSearch() {
        return pageProductoSearch;
    }

    public void setPageProductoSearch(String pageProductoSearch) {
        this.pageProductoSearch = pageProductoSearch;
    }

    public List<Cproducto_fnd> getSelectedProductoDTO_fnd_facturacion() {
        return SelectedProductoDTO_fnd_facturacion;
    }

    public void setSelectedProductoDTO_fnd_facturacion(List<Cproducto_fnd> SelectedProductoDTO_fnd_facturacion) {
        this.SelectedProductoDTO_fnd_facturacion = SelectedProductoDTO_fnd_facturacion;
    }

    public String getPageBuscarDoc() {
        return pageBuscarDoc;
    }

    public void setPageBuscarDoc(String pageBuscarDoc) {
        this.pageBuscarDoc = pageBuscarDoc;
    }

    public String getPageConsumidorFinal() {
        return pageConsumidorFinal;
    }

    public String getPageconsufiProductoSearch() {
        return pageconsufiProductoSearch;
    }

    public void setPageconsufiProductoSearch(String pageconsufiProductoSearch) {
        this.pageconsufiProductoSearch = pageconsufiProductoSearch;
    }

    public void setPageConsumidorFinal(String pageConsumidorFinal) {
        this.pageConsumidorFinal = pageConsumidorFinal;
    }

    public String getPageconsufiCTipoClienteSearch() {
        return pageconsufiCTipoClienteSearch;
    }

    public String getPagedevoconsufiCTipoClienteSearch() {
        return pagedevoconsufiCTipoClienteSearch;
    }

    public void setPagedevoconsufiCTipoClienteSearch(String pagedevoconsufiCTipoClienteSearch) {
        this.pagedevoconsufiCTipoClienteSearch = pagedevoconsufiCTipoClienteSearch;
    }

    public String getPagedevoconsufiProductoSearch() {
        return pagedevoconsufiProductoSearch;
    }

    public void setPagedevoconsufiProductoSearch(String pagedevoconsufiProductoSearch) {
        this.pagedevoconsufiProductoSearch = pagedevoconsufiProductoSearch;
    }

    public String getPagedevoConsumidorFinal() {
        return pagedevoConsumidorFinal;
    }

    public void setPagedevoConsumidorFinal(String pagedevoConsumidorFinal) {
        this.pagedevoConsumidorFinal = pagedevoConsumidorFinal;
    }

    public String getPagecfiscalCTipoClienteSearch() {
        return pagecfiscalCTipoClienteSearch;
    }

    public void setPagecfiscalCTipoClienteSearch(String pagecfiscalCTipoClienteSearch) {
        this.pagecfiscalCTipoClienteSearch = pagecfiscalCTipoClienteSearch;
    }

    public String getPagecfiscalProductoSearch() {
        return pagecfiscalProductoSearch;
    }

    public void setPagecfiscalProductoSearch(String pagecfiscalProductoSearch) {
        this.pagecfiscalProductoSearch = pagecfiscalProductoSearch;
    }

    public String getPageCreditoFiscal() {
        return pageCreditoFiscal;
    }

    public void setPageCreditoFiscal(String pageCreditoFiscal) {
        this.pageCreditoFiscal = pageCreditoFiscal;
    }

    public String getPageNotaCreditoFac() {
        return pageNotaCreditoFac;
    }

    public void setPageNotaCreditoFac(String pageNotaCreditoFac) {
        this.pageNotaCreditoFac = pageNotaCreditoFac;
    }

    public String getPagefacordenCTipoClienteSearch() {
        return pagefacordenCTipoClienteSearch;
    }

    public void setPagefacordenCTipoClienteSearch(String pagefacordenCTipoClienteSearch) {
        this.pagefacordenCTipoClienteSearch = pagefacordenCTipoClienteSearch;
    }

    public String getPagefacordenProductoSearch() {
        return pagefacordenProductoSearch;
    }

    public void setPagefacordenProductoSearch(String pagefacordenProductoSearch) {
        this.pagefacordenProductoSearch = pagefacordenProductoSearch;
    }

    public void setPageconsufiCTipoClienteSearch(String pageconsufiCTipoClienteSearch) {
        this.pageconsufiCTipoClienteSearch = pageconsufiCTipoClienteSearch;
    }

}
