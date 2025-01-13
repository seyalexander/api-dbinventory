package com.dbperu.dbinventory.Models.Dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbperu.dbinventory.Models.Entity.CargaExcels;
import com.dbperu.dbinventory.Models.Entity.CargaExcelsId;

@Repository
public interface CagarExcelsRepository extends JpaRepository<CargaExcels, CargaExcelsId>  {

}
