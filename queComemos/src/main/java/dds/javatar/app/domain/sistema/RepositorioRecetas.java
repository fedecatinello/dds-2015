package dds.javatar.app.domain.sistema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import static java.util.Arrays.asList;
import dds.javatar.app.db.DBContentProvider;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.BusinessException;


public class RepositorioRecetas extends DBContentProvider<Receta> implements InterfazRepositorioRecetas {

	public List<Receta> recetaConocidas;

	protected RepositorioRecetas() {
		this.collectionName = "Recetas";
		this.recetaConocidas = new ArrayList<Receta>();
	}

	private static RepositorioRecetas instance;

	public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
		}
		return instance;
	}

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
			ingredientes.addAll(receta.getIngredientes().keySet());
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

	
	@Override   
	public Document create(Receta receta) {
		
		Document bson = new Document("_id", new ObjectId()).append("nombre", receta.getNombre());
		
		/* Append author if applies */
		this.appendAuthorIfApply(bson, receta);
		
	
		return bson.append("calorias", receta.getCalorias())
			.append("dificultad", receta.getDificultad())
			.append("temporada", receta.getTemporada())
			.append("tiempoPreparacion", receta.getTiempoPreparacion())
			.append("anioCreacion", receta.getAnioCreacion())
			.append("condimentos", asList(new BasicDBObject(receta.getCondimentos())))
			.append("ingredientes", asList(new BasicDBObject(receta.getIngredientes())))
			.append("pasosPreparacion", asList(new BasicDBObject(receta.getPasosPreparacion())))
			.append("condiciones", receta.getCondiciones()); 
		/**TODO FIX**/
									
	}
	
	public List<String> getCondicionesName(Set<CondicionPreexistente> condicionesPreexistentes){
		List<String> condiciones = new ArrayList<String>();
		for(CondicionPreexistente cond : condicionesPreexistentes){
			condiciones.add(cond.getName());
		}
		return condiciones;
	}
	
	public void appendAuthorIfApply(Document bson, Receta receta) {
		if(receta.getAutor()!= null) {
			bson.append("autor", receta.getAutor());
		}
	}
	

	@Override
	public Document createFilter(Receta t) {
		
		/** Creamos un filtro para buscar recetas por nombre y autor **/		
		Document bson = new Document("nombre", t.getNombre());
		
		/* Append author if applies */
		this.appendAuthorIfApply(bson, t);
		
		return bson;
	}


	@Override
	public Receta map(Document bson) {
		
		Receta receta = new Receta();
		
		receta.setNombre(bson.getString("nombre"));
		this.mapAuthorIfApply(bson, receta);
		receta.setCalorias(bson.getInteger("calorias"));
		receta.setDificultad(bson.getString("dificultad"));
		receta.setTemporada(bson.getString("temporada"));
		receta.setTiempoPreparacion(bson.getInteger("tiempoPreparacion"));
		receta.setAnioCreacion(bson.getInteger("anioCreacion"));
		
		// TODO Faltan mapear las colecciones
		receta.setCondiciones((Set<CondicionPreexistente>)bson.get("condiciones"));
		return receta;
	}
	
	public void mapAuthorIfApply(Document bson, Receta receta) {
		if(bson.containsKey("autor")) {
			receta.setAutor(bson.getString("autor"));
		}
	}

}
