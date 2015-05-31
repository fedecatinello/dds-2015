package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPrivadaSimple extends RecetaSimple implements RecetaPrivada {

	/**** builders ****/
	public RecetaPrivadaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.pasosPreparacion = new HashMap<Integer, String>();
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


	@Override
	public Receta privatizarSiCorresponde(Usuario usuario)
			throws UsuarioException, RecetaException {
		return this;
	}

}
