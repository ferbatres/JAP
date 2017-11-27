/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.mbean;

import com.jap.client.dto.Calmacen;
import com.jap.client.dto.Ccliente;
import com.jap.client.dto.Cempleado;
import com.jap.client.dto.Cfpago;
import com.jap.client.dto.Cproducto;
import com.jap.client.dto.CtipoDocumento;
import com.jap.client.dto.PArticulos;
import com.jap.client.dto.VFacturas;
import com.jap.client.utils.ClientUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author fernando_batres
 */
@ManagedBean(name = "facturacionMBean")
@ViewScoped
public class PFacturacionMBean extends ClientUtils implements Serializable {

    CclienteMBean bean = new CclienteMBean();

    private Cproducto producto;
    private Cfpago fpago;
    private Ccliente clienteOE;
    private List<PArticulos> facturaList;
    private List<PArticulos> list;
    private List<PArticulos> filteredList;
    private List<PArticulos> selectedList;
    private List<PArticulos> resultList;
    private List<Cfpago> listFpago;
    private List<CtipoDocumento> listTdoc;
    private List<Calmacen> listSuc;
    private List<Cempleado> listVend;
    private Double totalPrecio;
    private Double totalCantidad;
    private Double totalPrecioArticulo;
    private Double totalCantidadArticulo;
    private Boolean agrupado;
    private String selectedFPago;
    private String selectedidpago;
    private String empleadoCheck;
    private String sucursalCheck;
    private String globalIdDocumento;
    private final String ordenEntrega = "1";
    private final String consumidorFinal = "2";
    private final String borrador = "BORRADOR";
    private final String activo = "ACTIVO";

    private String selFPago = "";
    private String selTDoc = "";
    private String selSuc = "";
    private String selVend = "";

    private VFacturas documento;
    private VFacturas documentoFiltrar;
    private List<VFacturas> listDocumento;
    private Date desde;
    private Date hasta;

    public PFacturacionMBean() {
        resultList = new ArrayList<>();
        listDocumento = new ArrayList<>();
        documentoFiltrar = new VFacturas();
        clienteOE = new Ccliente();
        facturaList = new ArrayList<>();
        this.totalCantidad = 0.0;
        this.totalPrecio = 0.0;
        this.selFPago = "";
        this.selTDoc = "";
        this.selSuc = "";
        this.selVend = "";
        this.globalIdDocumento = "";
        loadFormaPago();
        loadCtipoAlmacen();
        loadCtipoDocumento();
        loadCtipoEmpleado();
    }

    public void showNewOE() {
        System.out.println("mostrando  nueva orden de entrega");
        bean.cleanClienteForm();
        bean.cleanObjects();
        facturaList = null;
        selectedList = null;
        loadFormaPago();
        setEmpleadoCheck(getEmpleado().getNombre());
        setSucursalCheck(getEmpleado().getIdSucursal());
    }

    public List<PArticulos> articuloList() {
        System.out.println("llenando articuloList");
        agrupado = Boolean.FALSE;
        list = new ArrayList<>();
        String param = "{}";
        String url = "";
        if (!agrupado) {
            url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/fcproductoarticulos";
        } else {
            url = getHttpSegment() + "/WsRestFullJap/jap/cproducto/fcproductoarticulos";
        }
        try {
            String result = ClientRestFullResponse(url, param).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("dato");
            for (int i = 0; i < arr.length(); i++) {
                PArticulos c = new PArticulos();
                c.setId(arr.getJSONObject(i).get("id").toString());
                c.setCodigo(arr.getJSONObject(i).get("codigo").toString());
                c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
                c.setMarca(arr.getJSONObject(i).get("marca").toString());
                c.setModelo(arr.getJSONObject(i).get("modelo").toString());
                c.setPrecio(arr.getJSONObject(i).get("precio").toString());
                c.setStock(arr.getJSONObject(i).get("stock").toString());
                c.setSucursal(arr.getJSONObject(i).get("sucursal").toString());
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<VFacturas> documentoList() {
        System.out.println("Llenando lista de facturas ingresadas");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JSONObject param = new JSONObject();
        param.put("idEmpresa", getEmpleado().getIdEmpresa());
        param.put("correlativo", documentoFiltrar.getCorrelativo());
        if (this.selTDoc != null && !this.selTDoc.isEmpty()) {
            param.put("tipoDocumento", this.selTDoc);
        } else {
            param.put("tipoDocumento", "");
        }
        if (this.selFPago != null && !this.selFPago.isEmpty()) {
            param.put("formaPago", this.selFPago);
        } else {
            param.put("formaPago", "");
        }
        if (this.desde != null) {
            param.put("fechaDesde", formatter.format(this.desde));
        } else {
            param.put("fechaDesde", "");
        }
        if (this.hasta != null) {
            param.put("fechaHasta", formatter.format(this.hasta));
        } else {
            param.put("fechaHasta", "");
        }
        param.put("cliente", documentoFiltrar.getIdCliente());
        if (this.selSuc != null && !this.selSuc.isEmpty()) {
            param.put("sucursal", this.selSuc);
        } else {
            param.put("sucursal", "");
        }
        if (this.selVend != null && !this.selVend.isEmpty()) {
            param.put("vendedor", this.selVend);
        } else {
            param.put("vendedor", "");
        }
        System.out.println("parametros entrada" + param.toString());
        String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturacion/fpfacturacionbyFilters";
        listDocumento = new ArrayList<>();
        try {
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("dato");
            for (int i = 0; i < arr.length(); i++) {
                VFacturas documento = new VFacturas();
                documento.setEmpresa(arr.getJSONObject(i).get("empresa").toString());
                documento.setIdSucursal(arr.getJSONObject(i).get("idSucursal").toString());
                documento.setSucursal(arr.getJSONObject(i).get("sucursal").toString());
                documento.setCorrelativo(arr.getJSONObject(i).get("correlativo").toString());
                documento.setIdTipoDocumento(arr.getJSONObject(i).get("idTipoDocumento").toString());
                documento.setTipoDocumento(arr.getJSONObject(i).get("tipoDocumento").toString());
                documento.setFecha(arr.getJSONObject(i).get("fecha").toString());
                documento.setIdCliente(arr.getJSONObject(i).get("idCliente").toString());
                documento.setCliente(arr.getJSONObject(i).get("cliente").toString());
                documento.setTotal(arr.getJSONObject(i).get("total").toString());
                documento.setIdFormaPago(arr.getJSONObject(i).get("idFormaPago").toString());
                documento.setFormaPago(arr.getJSONObject(i).get("formaPago").toString());
                documento.setIdVendedor(arr.getJSONObject(i).get("idVendedor").toString());
                documento.setVendedor(arr.getJSONObject(i).get("vendedor").toString());
                documento.setEstado(arr.getJSONObject(i).get("estado").toString());
                listDocumento.add(documento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDocumento;
    }

    public void cleanArticulos() {
        System.out.println("entro a cleanArticulos");
        this.facturaList = new ArrayList<>();
        this.selectedList = new ArrayList<>();
    }

    public void cleanFactura() {
        bean.cleanClienteForm();
        facturaList = null;
        selectedList = null;
        totalPrecioArticulo = null;
        totalCantidadArticulo = null;
        selectedidpago = null;
        filteredList = null;
        list = null;
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
    }

    public void cleanDocs() {
//        this.listDocumento = new ArrayList<>();
        this.documento = new VFacturas();
    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println("onRowEdit");
        recalcular();
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("onRowCancel");
        recalcular();
    }

    public void onRowEditArticulo(RowEditEvent event) {
        System.out.println("onRowEditArticulo");
        this.resultList = this.facturaList;
        recalcularArticulo();
        recalcular();
    }

    public void onRowCancelArticulo(RowEditEvent event) {
        System.out.println("onRowCancelArticulo");
        recalcularArticulo();
    }

    public void agregar() {
        System.out.println("Agregar");
        for (PArticulos articulo : selectedList) {
            PArticulos nuevo = new PArticulos();
            nuevo.setId(articulo.getId());
            nuevo.setCodigo(articulo.getCodigo());
            nuevo.setDescripcion(articulo.getDescripcion());
            nuevo.setMarca(articulo.getMarca());
            nuevo.setModelo(articulo.getModelo());
            nuevo.setPrecio(articulo.getPrecio());
            nuevo.setStock(articulo.getStock());
            nuevo.setSucursal(articulo.getSucursal());
            nuevo.setCantidad("1");
            System.out.println("Agregando articulo a resultList: " + nuevo.getDescripcion());
            this.resultList.add(nuevo);
        }
        recalcular();
    }

    public void trasladar() {
        System.out.println("Trasladando lista a factura");
        this.facturaList = this.resultList;
        recalcularArticulo();
    }

    public void remover(PArticulos articulo) {
        System.out.println("Removiendo articulo de resultList: " + articulo.getDescripcion());
        this.resultList.remove(articulo);
        recalcular();
    }

    public void removerArticulo(PArticulos articulo) {
        System.out.println("Removiendo articulo de facturaList: " + articulo.getDescripcion());
        this.facturaList.remove(articulo);
        this.resultList = this.facturaList;
        recalcularArticulo();
        recalcular();
    }

    public void recalcular() {
        Double totalPrecioActual = new Double(0);
        Double totalCantidadActual = new Double(0);
        for (int i = 0; i < resultList.size(); i++) {
            totalPrecioActual = totalPrecioActual + (Double.parseDouble(resultList.get(i).getPrecio()) * Double.parseDouble(resultList.get(i).getCantidad()));
            totalCantidadActual = totalCantidadActual + Double.parseDouble(resultList.get(i).getCantidad());
        }
        this.totalCantidad = totalCantidadActual;
        this.totalPrecio = totalPrecioActual;
    }

    public void recalcularArticulo() {
        Double totalPrecioActual = new Double(0);
        Double totalCantidadActual = new Double(0);
        for (int i = 0; i < facturaList.size(); i++) {
            totalPrecioActual = totalPrecioActual + (Double.parseDouble(facturaList.get(i).getPrecio()) * Double.parseDouble(facturaList.get(i).getCantidad()));
            totalCantidadActual = totalCantidadActual + Double.parseDouble(facturaList.get(i).getCantidad());
        }
        this.totalCantidadArticulo = totalCantidadActual;
        this.totalPrecioArticulo = totalPrecioActual;
    }

    public void guardar(Ccliente clienteOE, List<PArticulos> facturaList, String empleadoCheck, String operacion, String edoFactura) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
            if (clienteOE.getNombre() == null) {
                addsimplemessageserror("Debe Seleccionar un Cliente");
            }
            if (ValidNullString(getSelectedidpago())) {
                addsimplemessageserror("Debe Seleccionar Forma de Pago");
            }
            if (facturaList != null) {
                JSONObject param = new JSONObject();
                JSONObject obj;
                globalIdDocumento = getFacturacionId();
                param.put("idEmpresa", getEmpleado().getIdEmpresa());
                param.put("idSucursal", getEmpleado().getSucursal());
                param.put("id", globalIdDocumento);
                param.put("idTipoFactura", operacion);
                param.put("idCliente", clienteOE.getId());
                param.put("direccionCliente", clienteOE.getDireccion());
                param.put("idCiudad", "1");
                param.put("idMunicipio", clienteOE.getIdMunicipio());
                param.put("idDepto", clienteOE.getIdDepto());
                param.put("registroFiscal", clienteOE.getRegistroFiscal());
                param.put("nit", clienteOE.getNit());
                param.put("idFormaPago", this.selectedidpago);
                param.put("idVendedor", getEmpleado().getId().trim());
                param.put("idCcfGenerar", "");
                param.put("idTaller", "");
                param.put("expediente", "");
                param.put("placa", "");
                param.put("fechaFactura", formatter.format(today));
                param.put("estado", edoFactura);
                String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturacion/spfacturacion";
                System.out.println("param.toString()" + param.toString());
                String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class
                );
                obj = new JSONObject(result);

                addsimplemessages(
                        (obj.length() != 0 ? obj.get("dato").toString() : "Documento Guardado con Exito Borrador"));
                guardarDetalle(facturaList, globalIdDocumento, getEmpleado().getSucursal(), getEmpleado().getIdEmpresa());
            } else {
                addsimplemessageserror("Debe Seleccionar Articulos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addsimplemessageserror("Ocurrió un error al guardar el documento");
        }

    }

    public void descargarInventario(List<PArticulos> articulos, String idFactura) {
//        try {
//            JSONObject param = new JSONObject();
//            JSONObject obj;
//            String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturaciondetail/sfactdetail";
//            for (PArticulos articulo : facturaList) {
//                param.put("idEmpresa", empresa);
//                param.put("idAlmacen", almacen);
//                param.put("idFactura", idFactura);
//                param.put("idProd", articulo.getId());
//                param.put("qty", articulo.getCantidad());
//                param.put("precioUnitario", articulo.getPrecio());
//                param.put("status", "A");
//                String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
//                obj = new JSONObject(result);
//                System.out.println("resultado detalle" + obj.get("dato").toString());
//            }
//        } catch (Exception e) {
//            addsimplemessageserror("Ocurrió un error al guardar el detalle del documento");
//            e.printStackTrace();
//        }
    }

    public void cambiarEstado(String estado, String idDocumento) {
        JSONObject param = new JSONObject();
        JSONObject obj;
        String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturacion/actualizarfactura";
        try {
            param.put("id_empresa", getEmpleado().getIdEmpresa());
            param.put("id_almacen", getEmpleado().getSucursal());
            param.put("id_documento", idDocumento);
            param.put("estado", estado);
            String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
            obj = new JSONObject(result);
            addsimplemessages(
                    (obj.length() != 0 ? obj.get("dato").toString() : "Documento Generado con Exito"));

        } catch (Exception e) {
            addsimplemessageserror("Ocurrió un error al cambiar el estado del documento");
            e.printStackTrace();
        }
    }

    public void guardarDetalle(List<PArticulos> facturaList, String idFactura, String almacen, String empresa) {
        try {
            JSONObject param = new JSONObject();
            JSONObject obj;
            String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturaciondetail/sfactdetail";
            for (PArticulos articulo : facturaList) {
                param.put("idEmpresa", empresa);
                param.put("idAlmacen", almacen);
                param.put("idFactura", idFactura);
                param.put("idProd", articulo.getId());
                param.put("qty", articulo.getCantidad());
                param.put("precioUnitario", articulo.getPrecio());
                param.put("status", "A");
                String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
                obj = new JSONObject(result);
                System.out.println("resultado detalle" + obj.get("dato").toString());
            }
        } catch (Exception e) {
            addsimplemessageserror("Ocurrió un error al guardar el detalle del documento");
            e.printStackTrace();
        }
    }

    public String getFacturacionId() {
        JSONObject param = new JSONObject();
        String id = "";
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/pfacturacion/nextid";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class
        );
        JSONObject obj = new JSONObject(result);
        id = obj.get("dato").toString();
        return id;
    }

    public void loadFormaPago() {
        System.out.println("Llenando Forma de Pago");
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/formadepago/fcformadepago";
        String result = ClientRestFullResponse(url, param).getEntity(String.class
        );
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

    private void loadCtipoEmpleado() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/cempleado/fcempleado";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        listVend = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Cempleado c = new Cempleado();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("nombre").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            listVend.add(c);
        }
    }

    private void loadCtipoAlmacen() {
        JSONObject param = new JSONObject();
        param.put("id_empresa", getId_empresa());
        String url = getHttpSegment() + "/WsRestFullJap/jap/calmacen/fcalmacen";
        String result = ClientRestFullResponse(url, param.toString()).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        listSuc = new ArrayList<>();
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
            listSuc.add(c);
        }
    }

    private void loadCtipoDocumento() {
        String param = "{}";
        String url = getHttpSegment() + "/WsRestFullJap/jap/ctipodoc/fctipodoc";
        String result = ClientRestFullResponse(url, param).getEntity(String.class);
        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("dato");
        listTdoc = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            CtipoDocumento c = new CtipoDocumento();
            c.setId(arr.getJSONObject(i).get("id").toString());
            c.setDescripcion(arr.getJSONObject(i).get("descripcion").toString());
            c.setStatus(arr.getJSONObject(i).get("status").toString());
            listTdoc.add(c);
        }
    }

    public void imprimir() {
    }

    public void anular() {
    }

    public void descartar() {
    }

    public void devolver() {
    }

    public void buscarFactura() {
    }

    public void generar(List<PArticulos> facturaList) {
        cambiarEstado(activo, this.globalIdDocumento);
        descargarInventario(facturaList, this.globalIdDocumento);
        cleanFactura();
    }

    //Accesores
    public Cproducto getProducto() {
        return producto;
    }

    public void setProducto(Cproducto producto) {
        this.producto = producto;
    }

    public List<PArticulos> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<PArticulos> facturaList) {
        this.facturaList = facturaList;
    }

    public List<PArticulos> getList() {
        return list;
    }

    public void setList(List<PArticulos> list) {
        this.list = list;
    }

    public List<PArticulos> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<PArticulos> filteredList) {
        this.filteredList = filteredList;
    }

    public List<PArticulos> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<PArticulos> selectedList) {
        this.selectedList = selectedList;
    }

    public Boolean getAgrupado() {
        return agrupado;
    }

    public void setAgrupado(Boolean agrupado) {
        this.agrupado = agrupado;
    }

    public List<PArticulos> getResultList() {
        return resultList;
    }

    public void setResultList(List<PArticulos> resultList) {
        this.resultList = resultList;
    }

    public Double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(Double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public Double getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(Double totalCantidad) {
        this.totalCantidad = totalCantidad;
    }

    public Double getTotalPrecioArticulo() {
        return totalPrecioArticulo;
    }

    public void setTotalPrecioArticulo(Double totalPrecioArticulo) {
        this.totalPrecioArticulo = totalPrecioArticulo;
    }

    public Double getTotalCantidadArticulo() {
        return totalCantidadArticulo;
    }

    public void setTotalCantidadArticulo(Double totalCantidadArticulo) {
        this.totalCantidadArticulo = totalCantidadArticulo;
    }

    public String getSelectedFPago() {
        return selectedFPago;
    }

    public void setSelectedFPago(String selectedFPago) {
        this.selectedFPago = selectedFPago;
    }

    public List<Cfpago> getListFpago() {
        return listFpago;
    }

    public void setListFpago(List<Cfpago> listFpago) {
        this.listFpago = listFpago;
    }

    public Cfpago getFpago() {
        return fpago;
    }

    public void setFpago(Cfpago fpago) {
        this.fpago = fpago;
    }

    public VFacturas getDocumento() {
        return documento;
    }

    public void setDocumento(VFacturas documento) {
        this.documento = documento;
    }

    public List<VFacturas> getListDocumento() {
        return listDocumento;
    }

    public void setListDocumento(List<VFacturas> listDocumento) {
        this.listDocumento = listDocumento;
    }

    public String getSelectedidpago() {
        return selectedidpago;
    }

    public void setSelectedidpago(String selectedidpago) {
        this.selectedidpago = selectedidpago;
    }

    public String getSelFPago() {
        return selFPago;
    }

    public void setSelFPago(String selFPago) {
        this.selFPago = selFPago;
    }

    public String getSelTDoc() {
        return selTDoc;
    }

    public void setSelTDoc(String selTDoc) {
        this.selTDoc = selTDoc;
    }

    public String getSelSuc() {
        return selSuc;
    }

    public void setSelSuc(String selSuc) {
        this.selSuc = selSuc;
    }

    public String getSelVend() {
        return selVend;
    }

    public void setSelVend(String selVend) {
        this.selVend = selVend;
    }

    public List<CtipoDocumento> getListTdoc() {
        return listTdoc;
    }

    public void setListTdoc(List<CtipoDocumento> listTdoc) {
        this.listTdoc = listTdoc;
    }

    public List<Calmacen> getListSuc() {
        return listSuc;
    }

    public void setListSuc(List<Calmacen> listSuc) {
        this.listSuc = listSuc;
    }

    public List<Cempleado> getListVend() {
        return listVend;
    }

    public void setListVend(List<Cempleado> listVend) {
        this.listVend = listVend;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public VFacturas getDocumentoFiltrar() {
        return documentoFiltrar;
    }

    public void setDocumentoFiltrar(VFacturas documentoFiltrar) {
        this.documentoFiltrar = documentoFiltrar;
    }

    public String getOrdenEntrega() {
        return ordenEntrega;
    }

    public String getConsumidorFinal() {
        return consumidorFinal;
    }

    public String getBorrador() {
        return borrador;
    }

    public Ccliente getClienteOE() {
        return clienteOE;
    }

    public void setClienteOE(Ccliente clienteOE) {
        this.clienteOE = clienteOE;
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

    public String getGlobalIdDocumento() {
        return globalIdDocumento;
    }

    public void setGlobalIdDocumento(String globalIdDocumento) {
        this.globalIdDocumento = globalIdDocumento;
    }

}
