package com.dbperu.dbinventory.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbperu.dbinventory.Models.Entity.CabeceraCargaExcels;
import com.dbperu.dbinventory.Models.Services.ICabeceraCargaService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class CabeceraCargaDExcelsController {

	@Autowired
	private ICabeceraCargaService cabeceraCargaService;

	@GetMapping("/Inventarios")
	private List<CabeceraCargaExcels> index () {
		return cabeceraCargaService.listaCabereza();
	}

	@GetMapping("/Inventarios/{rucempresa}/{idCarga}")
    public ResponseEntity<CabeceraCargaExcels> obtenerCargaPorId( @PathVariable String rucempresa, @PathVariable Long idCarga) {
        try {
        	CabeceraCargaExcels carga = cabeceraCargaService.obtenerCargaPorId(rucempresa,idCarga);
            return ResponseEntity.ok(carga);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

	@PostMapping("/Inventarios/Cabecera")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registrarCabeceraCargaExcels(@RequestBody CabeceraCargaExcels cabeceraCargaExcels) {
	    try {
	    	CabeceraCargaExcels cabeceraGuardada = cabeceraCargaService.registrarCabeceraCargaExcels(cabeceraCargaExcels);

	        Map<String, Object> response = new HashMap();
	        response.put("message", "Cabecera registrada exitosamente");
	        response.put("idCarga", cabeceraGuardada.getIdcarga());
	        response.put("rucempresa", cabeceraGuardada.getRucempresa());

	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la cabecera: " + e.getMessage());
	    }
	}

	@GetMapping("/Inventarios/idCarga/{rucempresa}")
    public ResponseEntity<Long> obtenerIdCarga( @PathVariable String rucempresa) {
        try {
        	Long carga = cabeceraCargaService.obtenerUltimoIdCarga(rucempresa);
            return ResponseEntity.ok(carga);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	@PutMapping("/Inventarios/{rucempresa}/{idCarga}/usuarioAsignado")
	public ResponseEntity<?> actualizarUsuarioAsignado(
	        @PathVariable String rucempresa,
	        @PathVariable Long idCarga,
	        @RequestBody String usuarioAsignado) {
	    try {
	        CabeceraCargaExcels cargaActualizada = cabeceraCargaService.actualizarUsuarioAsignado(rucempresa, idCarga, usuarioAsignado);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Usuario asignado exitosamente");
	        response.put("idCarga", cargaActualizada.getIdcarga());
	        response.put("rucempresa", cargaActualizada.getRucempresa());
	        response.put("usuarioAsignado", cargaActualizada.getUsuarioAsignado());

	        return ResponseEntity.ok(response);
	    } catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carga no encontrada");
	    }
	}
	
	

}
