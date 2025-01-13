package com.dbperu.dbinventory.Models.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbperu.dbinventory.Models.Entity.Seguridad;
import com.dbperu.dbinventory.Models.Entity.SeguridadIds;

public interface SeguridadRepository extends JpaRepository<Seguridad, SeguridadIds>{
	Optional<Seguridad> findByRucempresaAndIdusuarioAndContrasenia(String rucempresa, String idusuario, String contrasenia);
}
