package com.dbperu.dbinventory.Models.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbperu.dbinventory.Models.Entity.Empresa;

@Repository
public interface EmpresasRepository extends JpaRepository<Empresa, String>{

}


