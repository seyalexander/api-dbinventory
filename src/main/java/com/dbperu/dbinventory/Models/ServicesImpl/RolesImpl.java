package com.dbperu.dbinventory.Models.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbperu.dbinventory.Models.Dao.RolRepository;
import com.dbperu.dbinventory.Models.Entity.Roles;
import com.dbperu.dbinventory.Models.Services.IRolService;

@Service
public class RolesImpl implements IRolService {
	
	private final RolRepository rolRepository;
	
	@Autowired
	public RolesImpl(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}
	
	@Override
	public List<Roles> listaRoles() {
		return rolRepository.findAll();
	}

}
