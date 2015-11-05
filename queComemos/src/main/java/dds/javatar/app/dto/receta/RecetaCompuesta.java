package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaCompuesta implements Receta {

	protected HashSet<Receta> subRecetas;
	protected HashMap<String, BigDecimal> condimentos;
	protected HashMap<String, BigDecimal> ingredientes;
	protected HashMap<Integer, String> pasosPreparacion;
	protected HashSet<CondicionPreexistente> condiciones;
	protected String nombre;
	protected String autor;
	protected String dificultad;
	protected Integer calorias;
	protected String temporada;
	protected Integer tiempoPreparacion;
	
	
	/* Getters & Setters */
	
	public HashSet<Receta> getSubRecetas() {
		return subRecetas;
	}
	public void setSubRecetas(HashSet<Receta> subRecetas) {
		this.subRecetas = subRecetas;
	}
	public HashMap<String, BigDecimal> getCondimentos() {
		return condimentos;
	}
	public void setCondimentos(HashMap<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
	}
	public HashMap<String, BigDecimal> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(HashMap<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public HashSet<CondicionPreexistente> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(HashSet<CondicionPreexistente> condiciones) {
		this.condiciones = condiciones;
	}
	
	public HashMap<Integer, String> getPasosPreparacion() {
		return pasosPreparacion;
	}
	public void setPasosPreparacion(HashMap<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	public Integer getCalorias() {
		return calorias;
	}
	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}
	public String getTemporada() {
		return temporada;
	}
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}
	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}
	
	/** Add Items **/
	
	public void agregarSubReceta(Receta subReceta)
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
			throw new RecetaException("La receta no es valida ya que esta vacia! (No tiene subrecetas)");
		}
	}
}
