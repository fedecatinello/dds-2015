package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;

import dds.javatar.app.dto.usuario.Usuario;

public class RecetaPrivadaSimple extends RecetaSimple implements RecetaPrivada {

	/**** builders ****/
	public RecetaPrivadaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
	}

	public RecetaPrivadaSimple(Integer calorias) {
		this();
		this.calorias = calorias;
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}

	public Boolean chequearModificacion(Receta receta, Usuario usuario) {
		return receta.chequearVisibilidad(receta, usuario);
	}

}
