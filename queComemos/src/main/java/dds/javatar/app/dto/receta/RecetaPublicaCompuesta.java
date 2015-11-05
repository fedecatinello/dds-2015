package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPublicaCompuesta extends RecetaCompuesta implements RecetaPublica {

	/** Constructor **/
	public RecetaPublicaCompuesta(
					String nombre,
					Integer calorias,
					String dificultad,
					String temporada,
					HashMap<String, BigDecimal> condimentos,
					HashMap<String, BigDecimal> ingredientes,
					HashMap<Integer, String> pasosPreparacion,
					HashSet<Receta> subrecetas)
	{
		this.nombre = nombre;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.condimentos = new HashMap<String, BigDecimal>();
		this.condimentos.putAll(condimentos);
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.ingredientes.putAll(ingredientes);
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.pasosPreparacion.putAll(pasosPreparacion);
		this.subRecetas = new HashSet<Receta>();
		this.subRecetas.addAll(subrecetas);
		this.agregarRecetaAlRepo(this);
	}
	
	@Override
	public void agregarRecetaAlRepo(RecetaPublica receta) {
		RepositorioRecetas.getInstance().agregar(receta);
	}

	public Receta privatizarSiCorresponde (Usuario user) throws UsuarioException, RecetaException{
		
		HashSet<Receta> recetasPrivatizadas = new HashSet<Receta>();
		
		for (Receta receta : this.subRecetas) {
			recetasPrivatizadas.add((RecetaPrivada) receta.privatizarSiCorresponde(user));
		}
		
		Receta recetaClonada = new RecetaBuilder(this.nombre)
						.dificultad(this.dificultad)
						.temporada(this.temporada)
						.agregarSubRecetas(recetasPrivatizadas)
						.buildReceta();
					
		user.agregarReceta(recetaClonada);
		user.quitarReceta(this);
		return recetaClonada;
		
	}

	@Override
	public RecetaPrivada clonarme(String autor) throws RecetaException {
		RecetaPrivada recetaClonada = (RecetaPrivada) new RecetaBuilder(this.nombre)
							.totalCalorias(this.calorias)
							.dificultad(this.dificultad)
							.temporada(this.temporada)
							.agregarSubRecetas(this.subRecetas)
							.agregarIngredientes(this.ingredientes)
							.agregarCondimentos(this.condimentos)
							.agregarPasos(this.pasosPreparacion)
							.inventadaPor(autor)
							.buildReceta();

		return recetaClonada;
	}

		
}
