package com.dbperu.dbinventory.Models.ServicesImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dbperu.dbinventory.Models.Dao.SeguridadRepository;
import com.dbperu.dbinventory.Models.Entity.Seguridad;
import com.dbperu.dbinventory.Models.Services.ISeguridadService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

import javax.crypto.SecretKey;


@Service
public class SeguridadImpl implements ISeguridadService{


	 // 1 hora: 3600000
    // 1 minuto: 60000

    @Value("${jwt.secret}")
    private String secretKey;

    private final Set<String> tokenBlacklist = new HashSet<>();

    private final SeguridadRepository seguridadRepository;

    @Autowired
    public SeguridadImpl(SeguridadRepository seguridadRepository) {
        this.seguridadRepository = seguridadRepository;
    }

    @Override
    public List<Seguridad> listaSeguridad() {
        return seguridadRepository.findAll();
    }

    @Override
    public Optional<Seguridad> validarLogin(String rucempresa, String idUsuario, String contrasena) {
        return seguridadRepository.findByRucempresaAndIdusuarioAndContrasenia(rucempresa, idUsuario, contrasena);
    }
    
    @Override
    @Transactional
    public Seguridad registrarUsuario(Seguridad usuario) {
    	
    	String tokenGenerado = generarToken(usuario.getIdusuario());
        usuario.setToken(tokenGenerado);
        
    	return seguridadRepository.save(usuario);
    }


    public String generarToken(String idUsuario) {
        byte[] keyBytes = secretKey.getBytes();

        if (keyBytes.length < 32) {
            SecretKey secretKey256 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            keyBytes = secretKey256.getEncoded();
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(idUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) 
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public boolean isTokenInBlacklist(String token) {
        return tokenBlacklist.contains(token);
    }


    public void addTokenToBlacklist(String token) {
        tokenBlacklist.add(token);
    }


    public boolean validarToken(String token) {
        try {

            Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);

         
            if (isTokenInBlacklist(token)) {
                System.out.println("Token está en la lista negra.");
                return false;
            }

            return true; 
        } catch (ExpiredJwtException e) {
         
            System.out.println("El token ha expirado.");
            return false;
        } catch (Exception e) {
         
            System.out.println("El token es inválido.");
            return false;
        }
    }
	
	
	

}
