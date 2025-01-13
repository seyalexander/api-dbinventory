package com.dbperu.dbinventory.Models.Services;

import java.util.List;

import com.dbperu.dbinventory.Models.Entity.Empresa;

public interface IEmpresasService {
	public List<Empresa> listaEmpresas();
	public Empresa regsitrarEmpresa(Empresa empresa);
}
