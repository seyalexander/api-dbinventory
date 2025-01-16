package com.dbperu.dbinventory.Models.Dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbperu.dbinventory.Models.Entity.CabeceraCargaExcels;
import com.dbperu.dbinventory.Models.Entity.CabeceraInventarioIds;


@Repository
public interface CargaCabeceraRepository extends JpaRepository<CabeceraCargaExcels, CabeceraInventarioIds> {
	@Query("SELECT c FROM CabeceraCargaExcels c LEFT JOIN FETCH c.detalle WHERE c.rucempresa = :rucempresa AND c.idcarga = :idCarga")
    Optional<CabeceraCargaExcels> findByIdCargaWithDetalles(@Param("rucempresa") String rucempresa, @Param("idCarga") Long idCarga);

	@Query("SELECT MAX(c.idcarga) FROM CabeceraCargaExcels c  WHERE c.rucempresa = :rucempresa")
	Optional<Long> findByRucEmpresa(@Param("rucempresa") String rucempresa);
	

}
