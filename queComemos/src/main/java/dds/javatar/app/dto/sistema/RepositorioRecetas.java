package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaSimple;
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
	public void updateReceta(Receta receta) {
		Receta recetaEncontrada = this.recetaConocidas.stream().filter(o -> o.getNombre().equals(receta.getNombre())).findFirst().get();
		
		recetaConocidas.remove(recetaEncontrada);
		recetaConocidas.add(receta);
		
	}
	
	public void eliminarTodasLasRecetas() {
		this.recetaConocidas.clear();
	}

}
