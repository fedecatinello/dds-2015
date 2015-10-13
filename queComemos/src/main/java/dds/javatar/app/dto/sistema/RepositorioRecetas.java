package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.exception.BusinessException;


public class RepositorioRecetas implements InterfazRepositorioRecetas {

	public List<Receta> recetaConocidas;

	protected RepositorioRecetas() {
		this.recetaConocidas = new ArrayList<Receta>();
	}

	private static RepositorioRecetas instance;

	public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
		}
		return instance;
	}

	/** Getter & Setter **/

	@Override
	public void agregar(Receta receta) {
		this.recetaConocidas.add(receta);
	}

	@Override
	public void quitar(Receta receta) throws BusinessException {
		this.recetaConocidas.remove(receta);
	}
	

	/** Metodos **/

	@Override
	public List<Receta> listarTodas() {
		return this.recetaConocidas;
	}
	
	@Override
	public Set<String> getAllIngredientes() {
		Set <String> ingredientes = new HashSet<String>();

		for (Receta receta : this.recetaConocidas) {
			ingredientes.addAll(getComponentesByNombre(receta.getIngredientes()));
		}
			return ingredientes;
	}

	@Override
	public void updateReceta(Receta receta) {
		Receta recetaEncontrada = this.recetaConocidas.stream().filter(o -> o.getNombre().equals(receta.getNombre())).findFirst().get();
		
		recetaConocidas.remove(recetaEncontrada);
		recetaConocidas.add(receta);
		
	}
	
	public void eliminarTodasLasRecetas() {
		this.recetaConocidas.clear();
	}
	
	public Set<String> getComponentesByNombre(List<Componente> componentes){
		Set<String> nombres = new HashSet<>();
		for(Componente componente:componentes){
			nombres.add(componente.getDescripcion());
		}
		return nombres;
	}

}
