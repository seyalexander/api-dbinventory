package com.dbperu.dbinventory.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbperu.dbinventory.Models.Entity.Roles;
import com.dbperu.dbinventory.Models.Services.IRolService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class RolesController {
	
	@Autowired
	private IRolService iRolService;
	
	@GetMapping("/Roles")
	public List<Roles> index() {
		return iRolService.listaRoles();	
	}
}
