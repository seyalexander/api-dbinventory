package com.dbperu.dbinventory.Models.Services;

import java.util.List;

import com.dbperu.dbinventory.Models.Entity.CabeceraCargaExcels;

public interface ICabeceraCargaService {
	public List<CabeceraCargaExcels> listaCabereza();
	public CabeceraCargaExcels obtenerCargaPorId(String rucempresa, Long idCarga);
	public CabeceraCargaExcels registrarCabeceraCargaExcels(CabeceraCargaExcels cabeceraCargaExcels);
	public Long obtenerUltimoIdCarga(String rucempresa);
	public CabeceraCargaExcels actualizarUsuarioAsignado(String rucempresa, Long idcarga, String usuarioAsignado);

}
