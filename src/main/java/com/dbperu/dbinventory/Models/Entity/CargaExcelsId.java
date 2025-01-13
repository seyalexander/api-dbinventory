package com.dbperu.dbinventory.Models.Entity;

import java.util.Objects;

public class CargaExcelsId {
	 private String rucempresa;
	    private Long id;
	    private double nroitem;

	    public String getRucempresa() {
	        return rucempresa;
	    }
	    public void setRucempresa(String rucempresa) {
	        this.rucempresa = rucempresa;
	    }
	    public Long getId() {
	        return id;
	    }
	    public void setId(Long id) {
	        this.id = id;
	    }
	    public double getNroitem() {
	        return nroitem;
	    }
	    public void setNroitem(double nroitem) {
	        this.nroitem = nroitem;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
				return true;
			}
	        if (o == null || getClass() != o.getClass()) {
				return false;
			}
	        CargaExcelsId that = (CargaExcelsId) o;
	        return Double.compare(that.nroitem, nroitem) == 0 &&
	                Objects.equals(rucempresa, that.rucempresa) &&
	                Objects.equals(id, that.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(rucempresa, id, nroitem);
	    }
}
