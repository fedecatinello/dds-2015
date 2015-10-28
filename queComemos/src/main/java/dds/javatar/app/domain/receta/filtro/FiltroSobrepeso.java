package dds.javatar.app.domain.receta.filtro;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;

public class FiltroSobrepeso extends FiltroTemplate {

	Boolean superaSobrepeso;

	Integer limiteCalorias;

	public boolean validator(Usuario usuario, Receta receta) {

		superaSobrepeso = usuario.getIMC(0).intValue() > 30;

		Integer caloriasExtra = receta.getCalorias() - limiteCalorias;

		return superaSobrepeso && caloriasExtra > 500;
	}

	/* Setters y Getters */

	public Boolean getSuperaSobrepeso() {
		return superaSobrepeso;
	}

	public void setSuperaSobrepeso(Boolean superaSobrepeso) {
		this.superaSobrepeso = superaSobrepeso;
	}

	public Integer getLimiteCalorias() {
		return limiteCalorias;
	}

	public void setLimiteCalorias(Integer limiteCalorias) {
		this.limiteCalorias = limiteCalorias;
	}

}
