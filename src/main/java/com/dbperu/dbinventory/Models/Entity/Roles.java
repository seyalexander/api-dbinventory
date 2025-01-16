package com.dbperu.dbinventory.Models.Entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="Roles")
@Entity
public class Roles implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol", nullable = false)
	private Long idRol;
	
	@Column(name = "rol", nullable = false)
	private String rol;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "usuariocreador")
	private String usuariocreador;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechacreacion")
	private Date fechacreacion;

	@Column(name = "usuariomodificador")
	private String usuariomodificador;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechamodificacion")
	private Date fechamodificacion;
	
	@PrePersist
	public void fechaCreacionActual() {
		fechacreacion = new Date();
		fechamodificacion = new Date();
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuariocreador() {
		return usuariocreador;
	}

	public void setUsuariocreador(String usuariocreador) {
		this.usuariocreador = usuariocreador;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getUsuariomodificador() {
		return usuariomodificador;
	}

	public void setUsuariomodificador(String usuariomodificador) {
		this.usuariomodificador = usuariomodificador;
	}

	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
