package dds.javatar.app.domain.sistema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Document create(Receta receta) {
			
		return new Document("nombre", receta.getNombre())
									.append("calorias", receta.getCalorias())
									.append("dificultad", receta.getDificultad())
									.append("temporada", receta.getTemporada())
									.append("tiempoPreparacion", receta.getTiempoPreparacion())
									.append("autor", receta.getAutor())
									.append("anioCreacion", receta.getAnioCreacion())
									.append("condimentos", asList(new BasicDBObject(receta.getCondimentos())))
									.append("ingredientes", asList(new BasicDBObject(receta.getIngredientes()))
									.append("pasosPreparacion", asList(new BasicDBObject(receta.getPasosPreparacion())) 
									.append("condiciones", new BasicDBList()); //FiX
									
	};
	
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
	public Document createFilter(Receta t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Receta map(Document bson) {
		// TODO Auto-generated method stub
		return null;
	}

}
