package com.dbperu.dbinventory.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="detallecarga")
@IdClass(CargaExcelsId.class)
public class CargaExcels {

	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcarga")
    private Long id = 1L;

	@Column(name = "almacen")
    private String almacen;

    @Column(name = "sucursal")
    private String sucursal;

    @Id
    @Column(name = "rucempresa")
    private String rucempresa;

    @Column(name = "nroitem")
    private double nroitem;

    @Column(name = "zona")
    private String zona;

    @Column(name = "pasillo")
    private String pasillo;

    @Column(name = "rack")
    private String rack;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "esagrupado")
    private String esagrupado;

    @Column(name = "codigogrupo")
    private String codigogrupo;

    @Column(name = "codigoproducto")
    private String Codigoproducto;

    @Column(name = "codbarraproducto")
    private String Codigobarra;

    @Column(name = "descripcionproducto")
    private String descripcionProducto;

    @Column(name = "unidadmedida")
    private String Unidad;

    @Column(name = "stocklogico")
    private Double stockL;

    @Column(name = "stockfisico")
    private Double stockF;
    
    @Column(name = "stockresultante")
    private Double stockresultante;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "rucempresa", referencedColumnName = "rucempresa", insertable = false, updatable = false),
        @JoinColumn(name = "idcarga", referencedColumnName = "idcarga", insertable = false, updatable = false)
    })
    private CabeceraCargaExcels cabecera;

	public Long getId() {
		return id;
	}
	public void setId(Long idcarga) {
		this.id = idcarga;
	}
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public Long getIdcarga() {
		return id;
	}
	public void setIdcarga(Long idcarga) {
		this.id = idcarga;
	}
	public String getRucempresa() {
		return rucempresa;
	}
	public void setRucempresa(String rucempresa) {
		this.rucempresa = rucempresa;
	}
	public double getNroitem() {
		return nroitem;
	}
	public void setNroitem(double nroitem) {
		this.nroitem = nroitem;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getPasillo() {
		return pasillo;
	}
	public void setPasillo(String pasillo) {
		this.pasillo = pasillo;
	}
	public String getRack() {
		return rack;
	}
	public void setRack(String rack) {
		this.rack = rack;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getEsagrupado() {
		return esagrupado;
	}
	public void setEsagrupado(String esagrupado) {
		this.esagrupado = esagrupado;
	}
	public String getCodigogrupo() {
		return codigogrupo;
	}
	public void setCodigogrupo(String codigogrupo) {
		this.codigogrupo = codigogrupo;
	}
	public String getCodigoproducto() {
		return Codigoproducto;
	}
	public void setCodigoproducto(String codigoproducto) {
		Codigoproducto = codigoproducto;
	}
	public String getCodigobarra() {
		return Codigobarra;
	}
	public void setCodigobarra(String codigobarra) {
		Codigobarra = codigobarra;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public String getUnidad() {
		return Unidad;
	}
	public void setUnidad(String unidad) {
		Unidad = unidad;
	}
	public Double getStockL() {
		return stockL;
	}
	public void setStockL(Double stockL) {
		this.stockL = stockL;
	}
	public Double getStockF() {
		return stockF;
	}
	public void setStockF(Double stockF) {
		this.stockF = stockF;
	}
	public Double getStockresultante() {
		return stockresultante;
	}
	public void setStockresultante(Double stockresultante) {
		this.stockresultante = stockresultante;
	}
	public CabeceraCargaExcels getCabecera() {
		return cabecera;
	}
	public void setCabecera(CabeceraCargaExcels cabecera) {
		this.cabecera = cabecera;
	}
	
}
