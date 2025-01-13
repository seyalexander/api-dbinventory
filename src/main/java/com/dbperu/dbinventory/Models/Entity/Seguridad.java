package com.dbperu.dbinventory.Models.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="Seguridad")
@Entity
@IdClass(SeguridadIds.class)
public class Seguridad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "rucempresa", nullable = false)
	private String rucempresa;

	@Id
	@Column(name = "idusuario", nullable = false)
	private String idusuario;

	@Column(name = "Nombreusuario")
	private String Nombreusuario;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "estado")
	private String estado;

	@Column(name = "contrasenia")
	private String contrasenia;

	@Column(name = "token")
	private String token;

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


	public String getRucempresa() {
		return rucempresa;
	}

	public void setRucempresa(String rucempresa) {
		this.rucempresa = rucempresa;
	}

	public String getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombreusuario() {
		return Nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		Nombreusuario = nombreusuario;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getContrasenia() {
		return contrasenia;
	}


	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsuariocreador() {
		return usuariocreador;
	}

	public void setUsuariocreador(String usuariocreador) {
		this.usuariocreador = usuariocreador;
	}


	public String getUsuariomodificador() {
		return usuariomodificador;
	}

	public void setUsuariomodificador(String usuariomodificador) {
		this.usuariomodificador = usuariomodificador;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
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
