package com.dbperu.dbinventory.Models.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbperu.dbinventory.Models.Dao.CargaCabeceraRepository;
import com.dbperu.dbinventory.Models.Entity.CabeceraCargaExcels;
import com.dbperu.dbinventory.Models.Services.ICabeceraCargaService;


@Service
public class CabeceraCargaExcelsImpl implements ICabeceraCargaService {

	@Autowired
	private final CargaCabeceraRepository cargaCabeceraRepository;


	 @Autowired
	 public CabeceraCargaExcelsImpl(CargaCabeceraRepository cargaCabeceraRepository) {
	     this.cargaCabeceraRepository = cargaCabeceraRepository;
	 }

	@Override
	@Transactional(readOnly = true)
	public List<CabeceraCargaExcels> listaCabereza() {
		return cargaCabeceraRepository.findAll();
	}

	@Override
	public CabeceraCargaExcels obtenerCargaPorId(String rucempresa, Long idCarga) {
	    return cargaCabeceraRepository.findByIdCargaWithDetalles(rucempresa, idCarga)
	            .orElseThrow(() -> new RuntimeException("Carga con id " + idCarga + " y RUC " + rucempresa + " no encontrada"));
	}

	@Override
	@Transactional
	public CabeceraCargaExcels registrarCabeceraCargaExcels(CabeceraCargaExcels cabeceraCargaExcels) {
		return cargaCabeceraRepository.save(cabeceraCargaExcels);
	}

	@Override
	public Long obtenerUltimoIdCarga(String rucempresa) {
	    Optional<Long> maxIdCarga = cargaCabeceraRepository.findByRucEmpresa(rucempresa);
	    return (maxIdCarga.isPresent() ? maxIdCarga.get() : 1L);
	}
	
	@Override
	@Transactional
	public CabeceraCargaExcels actualizarUsuarioAsignado(String rucempresa, Long idcarga, String usuarioAsignado) {
	    CabeceraCargaExcels carga = cargaCabeceraRepository.findByIdCargaWithDetalles(rucempresa, idcarga)
	            .orElseThrow(() -> new RuntimeException("Carga con id " + idcarga + " y RUC " + rucempresa + " no encontrada"));

	    carga.setUsuarioAsignado(usuarioAsignado); 
	    //carga.setFechamodificacion(new Date());  

	    return cargaCabeceraRepository.save(carga); 
	}
	


}
