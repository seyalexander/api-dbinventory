package com.dbperu.dbinventory.Models.Entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="cargas")
@Entity
@IdClass(CabeceraInventarioIds.class)
public class CabeceraCargaExcels implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "rucempresa", nullable = false)
	private String rucempresa;

	@Id
	@Column(name = "idcarga", nullable = false)
	private Long idcarga;

	@Column(name = "Descripcion")
	private String Descripcion;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechacarga")
	private Date fechacarga;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechainicio")
	private Date fechainicio;

	@Column(name = "totalregistros")
	private int totalregistros;

	@Column(name = "estado")
	private String estado;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fechacreacion")
	@Temporal(TemporalType.DATE)
	private Date fechacreacion;

	@Column(name = "usuariocreacion")
	private String usuariocreacion;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechamodificacion")
	private Date fechamodificacion;


	@Column(name = "usuariomodificacion")
	private String usuariomodificacion;

	@Column(name = "dependecarga")
	private Long dependecarga;

	@Column(name = "Usuarioasignado")
	private String UsuarioAsignado;
	
	@OneToMany(mappedBy = "cabecera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CargaExcels> detalle;

	@PrePersist
	public void fechaCreacionActual() {
		fechacarga = new Date();
		fechainicio = new Date();
		fechacreacion = new Date();
		fechamodificacion = new Date();
	}

	public String getRucempresa() {
		return rucempresa;
	}

	public void setRucempresa(String rucempresa) {
		this.rucempresa = rucempresa;
	}

	public Long getIdcarga() {
		return idcarga;
	}

	public void setIdcarga(Long idcarga) {
		this.idcarga = idcarga;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Date getFechacarga() {
		return fechacarga;
	}

	public void setFechacarga(Date fechacarga) {
		this.fechacarga = fechacarga;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public int getTotalregistros() {
		return totalregistros;
	}

	public void setTotalregistros(int totalregistros) {
		this.totalregistros = totalregistros;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getUsuariocreacion() {
		return usuariocreacion;
	}

	public void setUsuariocreacion(String usuariocreacion) {
		this.usuariocreacion = usuariocreacion;
	}

	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	public String getUsuariomodificacion() {
		return usuariomodificacion;
	}

	public void setUsuariomodificacion(String usuariomodificacion) {
		this.usuariomodificacion = usuariomodificacion;
	}

	public Long getDependecarga() {
		return dependecarga;
	}

	public void setDependecarga(Long dependecarga) {
		this.dependecarga = dependecarga;
	}

	public String getUsuarioAsignado() {
		return UsuarioAsignado;
	}

	public void setUsuarioAsignado(String usuarioAsignado) {
		UsuarioAsignado = usuarioAsignado;
	}

	public List<CargaExcels> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<CargaExcels> detalle) {
		this.detalle = detalle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
