package dds.javatar.app.domain.sistema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import static java.util.Arrays.asList;
import dds.javatar.app.db.DBContentProvider;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.util.exception.BusinessException;


public class RepositorioRecetas extends DBContentProvider implements InterfazRepositorioRecetas {

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


	@Override   
	public Document create(Object doc) {
		
		Receta receta = (Receta) doc;
		
		return new Document("nombre", receta.getNombre())
									.append("calorias", receta.getCalorias())
									.append("dificultad", receta.getDificultad())
									.append("temporada", receta.getTemporada())
									.append("tiempoPreparacion", receta.getTiempoPreparacion())
									.append("autor", receta.getAutor())
									.append("anioCreacion", receta.getAnioCreacion())
									.append("condimentos", asList(
																		new Document(new HashMap<String,Object>(receta.getCondimentos())))
									.append("ingredientes", asList(
																		new Document(new HashMap<String,Object>(receta.getIngredientes()))))
									.append("pasosPreparacion", asList(receta.getPasosPreparacion().keySet())) //FIX
									.append("condiciones", asList(receta.getCondiciones())); //FiX
									
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

}
