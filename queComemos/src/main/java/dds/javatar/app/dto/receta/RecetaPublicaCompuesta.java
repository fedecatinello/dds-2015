package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RecetaPublicaCompuesta implements RecetaPublica {

	private HashSet<Receta> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;

	
	/**		Builder			**/
	public RecetaPublicaCompuesta() {
		this.subRecetas = new HashSet<Receta>();
	}

	
	

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}
	
	public Map<String, BigDecimal> getCondimentos() {
		return this.condimentos;
	}

	public Map<String, BigDecimal> getIngredientes() {
		return this.ingredientes;
	}
	
	
	
	public void agregarCondimento(String condimento, BigDecimal cantidad) {
		this.condimentos.put(condimento, cantidad);
	}
	
	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}

	public void agregarSubReceta(Receta subReceta) {
		this.subRecetas.add(subReceta);
	}

	
	
	public Boolean contieneIngrediente(String ingrediente) {
		//copy&paste, chequear esto

		Boolean contieneIngredienteX=false;		
		for (Iterator<Receta> iterator = subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			receta.contieneIngrediente(ingrediente);			
		}
		// TODO: habria que chequear en las subrecetas?
		return contieneIngredienteX;
	}

	public Boolean contieneCondimento(String condimento) {
		// TODO: habria que chequear en las subrecetas?
		return this.condimentos.containsKey(condimento);
	}

}
