package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaCompuesta extends AbstractReceta {

	/** Faltaria el tema de las recetas con las subrecetas **/
	protected HashSet<Receta> subRecetas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "receta_condicion",
		joinColumns = @JoinColumn(name = "receta_id"),
		inverseJoinColumns = @JoinColumn(name = "condicion_id") )
	protected HashSet<CondicionPreexistente> condiciones;

	/* Getters & Setters */
	
	public HashSet<Receta> getSubRecetas() {
		return subRecetas;
	}
	public void setSubRecetas(HashSet<Receta> subRecetas) {
		this.subRecetas = subRecetas;
	}
	
	public HashSet<CondicionPreexistente> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(HashSet<CondicionPreexistente> condiciones) {
		this.condiciones = condiciones;
	}
	
	/** Add Items **/
	
	public void agregarSubReceta(Receta subReceta)
			throws RecetaException {
		subReceta.validarSiLaRecetaEsValida();
		this.subRecetas.add(subReceta);
	}
	
	/** Validadores **/
	
	public Boolean containsIngrediente(String ingrediente){
		Boolean tiene = false;
		for(Componente comp : this.ingredientes){
			if(comp.getDescripcion().equalsIgnoreCase(ingrediente)) tiene=true;
		}
		return tiene;
	}
	
	public Boolean containsCondimento(String condimento){
		Boolean tiene = false;
		for(Componente comp : this.ingredientes){
			if(comp.getDescripcion().equalsIgnoreCase(condimento)) tiene=true;
		}
		return tiene;
	}
	
	public Boolean contieneIngrediente(String ingrediente) {
		this.getIngredientes();
		return this.containsIngrediente(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		this.getCondimentos();
		return this.containsCondimento(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento,
			BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.containsIngrediente(alimento)) {
			return Boolean.FALSE;
		}
		return (this.getIngredienteByNombre(alimento).getCantidad().compareTo(cantidad) == 1);
	}
	
	public Componente getIngredienteByNombre(String nombre){
		for(Componente comp : this.getIngredientes()){
			if(comp.getDescripcion().equalsIgnoreCase(nombre)) return comp;
		}
		return null;
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
