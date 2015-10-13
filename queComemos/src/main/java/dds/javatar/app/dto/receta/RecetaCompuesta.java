package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaCompuesta extends AbstractReceta {

	protected HashSet<Receta> subRecetas;
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
