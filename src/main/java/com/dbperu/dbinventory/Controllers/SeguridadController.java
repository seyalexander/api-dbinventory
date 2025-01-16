package com.dbperu.dbinventory.Controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbperu.dbinventory.Models.Entity.Seguridad;
import com.dbperu.dbinventory.Models.Services.ISeguridadService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class SeguridadController {

	@Autowired
    private ISeguridadService seguridadService;

    @GetMapping("/Seguridad")
    public List<Seguridad> index() {
        return seguridadService.listaSeguridad();
    }

    /*@RequestMapping(value = "/Seguridad/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> login(@RequestParam String rucempresa, @RequestParam String idUsuario, @RequestParam String contrasena) {
        Optional<Seguridad> usuario = seguridadService.validarLogin(rucempresa, idUsuario, contrasena);

        if (usuario.isPresent()) {
            String token = seguridadService.generarToken(idUsuario);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    static class LoginResponse {
        private String token;
        private String message;

        public LoginResponse(String token, String message) {
            this.token = token;
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }
    }*/
    
    @RequestMapping(value = "/Seguridad/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> login(
            @RequestParam String rucempresa,
            @RequestParam String idUsuario,
            @RequestParam String contrasena) {

        Optional<Seguridad> usuario = seguridadService.validarLogin(rucempresa, idUsuario, contrasena);

        if (usuario.isPresent()) {
            Seguridad usuarioEncontrado = usuario.get();

            if (!"1".equals(usuarioEncontrado.getEstado())) {
                return ResponseEntity.status(403).body(Collections.singletonMap("message", "Usuario inactivo."));
            }

            String token = seguridadService.generarToken(idUsuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuarioEncontrado);
            response.put("message", "Login exitoso.");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Credenciales incorrectas."));
        }
    }


    private final Set<String> tokenBlacklist = new HashSet<>();

    @PostMapping("/Seguridad/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        tokenBlacklist.add(token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout exitoso");

        return ResponseEntity.ok(response);
    }

    public boolean isTokenInBlacklist(String token) {
        return tokenBlacklist.contains(token);
    }

    @PostMapping("/Seguridad/validarToken")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        boolean esValido = seguridadService.validarToken(token);
        if (!esValido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
        }

        return ResponseEntity.ok("Token válido.");
    }
    
    @PostMapping("/Seguridad/Save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registrarEmpresa(@RequestBody Seguridad usuario) {
	    try {
	    	Seguridad usuarioSave = seguridadService.registrarUsuario(usuario);

	        Map<String, Object> response = new HashMap();
	        response.put("message", "Usuario registrada exitosamente");
	        response.put("token", usuarioSave.getToken());
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario: " + e.getMessage());
	    }
	}

}
