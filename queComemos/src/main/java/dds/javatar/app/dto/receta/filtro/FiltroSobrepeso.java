package dds.javatar.app.dto.receta.filtro;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroSobrepeso extends FiltroTemplate {

	Integer sobrepeso;
	
	public boolean validator(Usuario usuario, Receta receta) {
		
		sobrepeso = usuario.getIMC(0).intValue();
		
		Integer caloriasExtra = receta.getCalorias() - sobrepeso;
		
		return caloriasExtra > 500;
	}

	/* Setters y Getters */
	
	public Integer getSobrepeso() {
		return sobrepeso;
	}

	public void setSobrepeso(Integer sobrepeso) {
		this.sobrepeso = sobrepeso;
	}

}
