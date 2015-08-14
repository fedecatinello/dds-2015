package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPrivadaCompuesta extends RecetaCompuesta implements RecetaPrivada {
	
	/** Constructor **/
		public RecetaPrivadaCompuesta(){
			this.condimentos = new HashMap<String, BigDecimal>();
			this.ingredientes = new HashMap<String, BigDecimal>();
			this.pasosPreparacion = new HashMap<Integer, String>();
			this.subRecetas = new HashSet<Receta>();
		}

	public Receta privatizarSiCorresponde(Usuario user) throws UsuarioException, RecetaException {
		return this;
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}

	public void agregarPasoPreparacion(Integer nroPaso, String paso) {
		this.pasosPreparacion.put(nroPaso, paso);
	}

	public void agregarCondimento(String condimento, BigDecimal cantidad) {
		this.condimentos.put(condimento, cantidad);
	}

}
