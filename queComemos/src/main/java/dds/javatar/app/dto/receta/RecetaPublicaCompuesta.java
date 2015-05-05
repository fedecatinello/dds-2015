package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class RecetaPublicaCompuesta implements RecetaPublica {

	private HashSet<Receta> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;
	private String nombre;

	
	/**		Builder			**/
	public RecetaPublicaCompuesta() {
		this.subRecetas = new HashSet<Receta>();
	}

	
	
	/**		get items			**/
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}
	
	public Map<String, BigDecimal> getCondimentos() {
		for (Iterator<Receta> iterator = subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.condimentos.putAll(receta.getCondimentos());			
		}
		return condimentos;
	}

	public Map<String, BigDecimal> getIngredientes() {
		for (Iterator<Receta> iterator = subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.ingredientes.putAll(receta.getIngredientes());			
		}
		return ingredientes;
	}
	
	
	/**		Add items	 **/
	public void agregarSubReceta(Receta subReceta) throws BusinessException  {
		subReceta.validarSiLaRecetaEsValida();
		this.subRecetas.add(subReceta);
	}

	
	/**		Validadores			**/
	public Boolean contieneIngrediente(String ingrediente) {
		this.getIngredientes();
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		this.getCondimentos();
		return this.condimentos.containsKey(condimento);
	}
	
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if(usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}
	
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