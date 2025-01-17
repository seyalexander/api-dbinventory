package com.dbperu.dbinventory.Models.Entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable 
public class CabeceraInventarioIds {

	 private String rucempresa;
	    private Long idcarga;

	    public CabeceraInventarioIds() {
	    }

	    public CabeceraInventarioIds(String rucempresa, Long idcarga) {
	        this.rucempresa = rucempresa;
	        this.idcarga = idcarga;
	    }

	    public String getRucempresa() {
	        return rucempresa;
	    }

	    public void setRucempresa(String rucempresa) {
	        this.rucempresa = rucempresa;
	    }

	    public Long getIdcarga() {
	        return idcarga;
	    }

	    public void setIdcarga(Long idcarga) {
	        this.idcarga = idcarga;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        CabeceraInventarioIds that = (CabeceraInventarioIds) o;
	        return Objects.equals(rucempresa, that.rucempresa) &&
	               Objects.equals(idcarga, that.idcarga);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(rucempresa, idcarga);
	    }
}
