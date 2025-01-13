package com.dbperu.dbinventory.Models.Entity;

import java.util.Objects;

public class SeguridadIds {
	private String rucempresa;

    private String idusuario;

    public String getRucempresa() {
        return rucempresa;
    }

    public void setRucempresa(String rucempresa) {
        this.rucempresa = rucempresa;
    }

    public String getIdcarga() {
        return idusuario;
    }

    public void setIdcarga(String idusuario) {
        this.idusuario = idusuario;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        SeguridadIds that = (SeguridadIds) o;
        return Objects.equals(rucempresa, that.rucempresa) &&
               Objects.equals(idusuario, that.idusuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rucempresa, idusuario);
    }
}
