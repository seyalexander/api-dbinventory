package com.dbperu.dbinventory.Models.Services;

import java.util.List;

import com.dbperu.dbinventory.Models.Entity.CargaExcels;

public interface ICargaExcelsService {
	 void saveAll(List<CargaExcels> cargaExcels);
	 List<CargaExcels> cargarDatosDesdeExcel(byte[] archivoExcel) throws Exception;

}
