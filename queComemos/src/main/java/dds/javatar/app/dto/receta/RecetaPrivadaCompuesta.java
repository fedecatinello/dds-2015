package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPrivadaCompuesta implements RecetaPrivada {

	protected HashSet<RecetaPrivada> subRecetas;
	protected Map<String, BigDecimal> condimentos;
	protected Map<String, BigDecimal> ingredientes;
	protected Map<Integer, String> pasosPreparacion;
	protected String nombre;
	protected String dificultad;
	protected String temporada;
	protected Integer tiempoPreparacion;

	/** Builder **/
	public RecetaPrivadaCompuesta() {
		this.subRecetas = new HashSet<RecetaPrivada>();
		this.ingredientes = new HashMap<String, BigDecimal>();
	}

	/** get items **/
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.nombre = temporada;
	}

	public String getDificultad() {
		return this.dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public Integer getCalorias() {
		int caloriasTotal = 0;
		for (Iterator<RecetaPrivada> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			caloriasTotal = caloriasTotal + receta.getCalorias();
		}
		return caloriasTotal;
	}

	public Set<RecetaPrivada> getSubRecetas() {
		return this.subRecetas;
	}

	public Map<String, BigDecimal> getCondimentos() {
		for (Iterator<RecetaPrivada> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.condimentos.putAll(receta.getCondimentos());
		}
		return condimentos;
	}

	public Map<String, BigDecimal> getIngredientes() {
		for (Iterator<RecetaPrivada> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.ingredientes.putAll(receta.getIngredientes());
		}
		return ingredientes;
	}
	
	public Map<Integer, String> getPasosPreparacion() {
		for (Iterator<RecetaPrivada> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.pasosPreparacion.putAll(receta.getPasosPreparacion());
		}
		return pasosPreparacion;
	}

	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	/** Add items **/
	public void agregarSubReceta(RecetaPrivada subReceta)
			throws RecetaException {
		subReceta.validarSiLaRecetaEsValida();
		this.subRecetas.add(subReceta);
	}

	/** Validadores **/
	public Boolean contieneIngrediente(String ingrediente) {
		this.getIngredientes();
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		this.getCondimentos();
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento,
			BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}

	@Override
	public void validarSiLaRecetaEsValida() throws RecetaException {
		if (this.subRecetas.isEmpty()) {
			throw new RecetaException(
					"La receta no es valida ya que esta vacia! (No tiene subrecetas)");
		}
	}

	@Override
	public Receta privatizarSiCorresponde(Usuario user)
			throws UsuarioException, RecetaException {
		return this;
	}

}
