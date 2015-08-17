package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPrivadaCompuesta extends RecetaCompuesta implements RecetaPrivada {
	
	/** Constructor **/
		public RecetaPrivadaCompuesta(
				String nombre,
				String autor,
				Integer calorias,
				HashMap<String, BigDecimal> condimentos,
				HashMap<String, BigDecimal> ingredientes,
				HashMap<Integer, String> pasosPreparacion,
				HashSet<Receta> subRecetas)
		{
			this.nombre = nombre;
			this.autor = autor;
			this.calorias = calorias;
			this.condimentos = new HashMap<String, BigDecimal>();
			this.condimentos.putAll(condimentos);
			this.ingredientes = new HashMap<String, BigDecimal>();
			this.ingredientes.putAll(ingredientes);
			this.pasosPreparacion = new HashMap<Integer, String>();
			this.pasosPreparacion.putAll(pasosPreparacion);
			this.subRecetas = new HashSet<Receta>();
			this.subRecetas.addAll(subRecetas);
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
