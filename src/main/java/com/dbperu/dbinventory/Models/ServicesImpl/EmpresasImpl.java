package com.dbperu.dbinventory.Models.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbperu.dbinventory.Models.Dao.EmpresasRepository;
import com.dbperu.dbinventory.Models.Entity.Empresa;
import com.dbperu.dbinventory.Models.Services.IEmpresasService;

import jakarta.transaction.Transactional;

@Service
public class EmpresasImpl implements IEmpresasService {

	private final EmpresasRepository empresasRepository;

	 @Autowired
	 public EmpresasImpl(EmpresasRepository empresasRepository) {
	     this.empresasRepository = empresasRepository;
	 }


	@Override
	public List<Empresa> listaEmpresas() {
		return empresasRepository.findAll();
	}

	@Override
	@Transactional
	public Empresa regsitrarEmpresa(Empresa empresa) {
		return empresasRepository.save(empresa);
	}

}
