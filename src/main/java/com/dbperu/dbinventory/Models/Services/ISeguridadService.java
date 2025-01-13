package com.dbperu.dbinventory.Models.Services;

import java.util.List;
import java.util.Optional;

import com.dbperu.dbinventory.Models.Entity.Seguridad;

public interface ISeguridadService {
	public List<Seguridad> listaSeguridad();
	public Optional<Seguridad> validarLogin(String rucempresa, String idUsuario, String contrasena);
	public String generarToken(String idUsuario);
	public boolean isTokenInBlacklist(String token);
	public boolean validarToken(String token);
	public Seguridad registrarUsuario(Seguridad usuario);
}
