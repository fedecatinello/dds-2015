package dds.javatar.app.domain.sistema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.despegar.integration.mongo.connector.*;

import dds.javatar.app.db.DBContentProvider;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.BusinessException;


public class RepositorioRecetas extends DBContentProvider<Receta> implements InterfazRepositorioRecetas {

	private static final String collection_name = "recetas";
	
	private static MongoCollection<Receta> receta_collection;
	
	public static MongoCollection<Receta> getInstance() {
		if (receta_collection == null) {
			RepositorioRecetas repo = new RepositorioRecetas(); // esta bien manejarlo asi?
			receta_collection = repo.buildCollection(collection_name, Receta.class);
		}
		return receta_collection;
	}
	
	@Override
	public void agregar(Receta receta) {
		buildCollection(collection_name, Receta.class);
		
		this.insert(receta_collection, receta);		
	}
	
	public void agregarTodas(List<Receta> recetas) {
		this.insertAll(receta_collection, recetas);
	}

	@Override
	public void quitar(Receta receta) throws BusinessException {
		this.deleteByName(receta_collection, receta.getNombre());
	}
	

	/** Metodos **/

	@Override
	public List<Receta> listarTodas() {
		return this.findAll(receta_collection);
	}
	
	@Override
	public Set<String> getAllIngredientes() {
		Set <String> ingredientes = new HashSet<String>();

		for (Receta receta : this.listarTodas()) {
			ingredientes.addAll(receta.getIngredientes().keySet());
		}
			return ingredientes;
	}

	@Override
	public void updateReceta(Receta receta) {
		this.update(receta_collection, receta);		
	}
	
	public void eliminarTodasLasRecetas() {
		this.deleteAll(receta_collection);
	}

	
	public List<String> getCondicionesName(Set<CondicionPreexistente> condicionesPreexistentes){
		List<String> condiciones = new ArrayList<String>();
		for(CondicionPreexistente cond : condicionesPreexistentes){
			condiciones.add(cond.getName());
		}
		return condiciones;
	}
	
	
}
