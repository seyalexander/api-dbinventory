package com.dbperu.dbinventory.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbperu.dbinventory.Models.Entity.Empresa;
import com.dbperu.dbinventory.Models.Services.IEmpresasService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class EmpresasController {

	@Autowired
	private IEmpresasService empresaService;


	@GetMapping("/Empresas")
	private List<Empresa> index () {
		return empresaService.listaEmpresas();
	}

	@PostMapping("/Empresa/Save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registrarEmpresa(@RequestBody Empresa empresa) {
	    try {
	    	Empresa empresaSave = empresaService.regsitrarEmpresa(empresa);

	        Map<String, Object> response = new HashMap();
	        response.put("message", "Empresa registrada exitosamente");

	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la empresa: " + e.getMessage());
	    }
	}
}
