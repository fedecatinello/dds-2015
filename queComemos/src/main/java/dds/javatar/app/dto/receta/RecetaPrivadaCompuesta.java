package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class RecetaPrivadaCompuesta implements RecetaPrivada {

	private HashSet<RecetaPrivada> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;
	private String nombre;

	/** Builder **/
	public RecetaPrivadaCompuesta() {
		this.subRecetas = new HashSet<RecetaPrivada>();

	}

	/** get items **/
	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Integer getCalorias() {
		int caloriasTotal = 0;
		for (Iterator<RecetaPrivada> iterator = this.subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = iterator.next();
			caloriasTotal = caloriasTotal + receta.getCalorias();
		}
		return caloriasTotal;
	}

	public Set<RecetaPrivada> getSubRecetas() {
		return this.subRecetas;
	}

	@Override
	public Map<String, BigDecimal> getCondimentos() {
		for (Iterator<RecetaPrivada> iterator = this.subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = iterator.next();
			this.condimentos.putAll(receta.getCondimentos());
		}
		return this.condimentos;
	}

	@Override
	public Map<String, BigDecimal> getIngredientes() {
		for (Iterator<RecetaPrivada> iterator = this.subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = iterator.next();
			this.ingredientes.putAll(receta.getIngredientes());
		}
		return this.ingredientes;
	}

	/** Add items **/
	public void agregarSubReceta(RecetaPrivada subReceta) throws BusinessException {
		subReceta.validarSiLaRecetaEsValida();
		this.subRecetas.add(subReceta);
	}

	/** Validadores **/
	@Override
	public Boolean contieneIngrediente(String ingrediente) {
		this.getIngredientes();
		return this.ingredientes.containsKey(ingrediente);
	}

	@Override
	public Boolean contieneCondimento(String condimento) {
		this.getCondimentos();
		return this.condimentos.containsKey(condimento);
	}

	@Override
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	@Override
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean chequearModificacion(Receta receta, Usuario usuario) {
		return receta.chequearVisibilidad(receta, usuario);
	}

	@Override
	public void validarSiLaRecetaEsValida() throws BusinessException {
		if (this.subRecetas.isEmpty()) {
			throw new BusinessException("La receta no es valida ya que esta vacia! (No tiene subrecetas)");
		}
	}
}
