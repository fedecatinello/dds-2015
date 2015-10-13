package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.*;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@DiscriminatorValue("SimplePublica")
public class RecetaPublicaSimple extends RecetaSimple implements RecetaPublica {

	/**** Constructor ****/
	public RecetaPublicaSimple(
			String nombre,
			Integer calorias,
			String dificultad,
			String temporada,
			HashMap<String, BigDecimal> ingredientes,
			HashMap<String, BigDecimal> condimentos,
			HashMap<Integer, String> pasosPreparacion) 
	{
		this.nombre = nombre;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.ingredientes.putAll(ingredientes);
		this.condimentos = new HashMap<String, BigDecimal>();
		this.condimentos.putAll(condimentos);
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.pasosPreparacion.putAll(pasosPreparacion);
		this.agregarRecetaAlRepo(this);
	}

	public RecetaPrivada clonarme(String autor) {
		RecetaPrivada recetaClonada = (RecetaPrivada) new RecetaBuilder(this.nombre)
							.totalCalorias(this.calorias)
							.dificultad(this.dificultad)
							.temporada(this.temporada)
							.agregarIngredientes(this.ingredientes)
							.agregarCondimentos(this.condimentos)
							.agregarPasos(this.pasosPreparacion)
							.inventadaPor(autor)
							.buildReceta();
		
		return recetaClonada;
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		return true;
	}

	@Override
	public void agregarRecetaAlRepo(RecetaPublica receta) {
		RepositorioRecetas.getInstance().agregar(receta);		
	}

	public Receta privatizarSiCorresponde (Usuario usuario) throws UsuarioException, RecetaException{
		Receta recetaClonada = new RecetaBuilder(this.nombre)
						.totalCalorias(this.calorias)
						.dificultad(this.dificultad)
						.temporada(this.temporada)
						.agregarIngredientes(this.ingredientes)
						.agregarCondimentos(this.condimentos)
						.agregarPasos(this.pasosPreparacion)
						.buildReceta();
			
		usuario.agregarReceta(recetaClonada);
		usuario.quitarReceta(this);
		return recetaClonada;
		
	}

	
	//Getters & Setters
	
	public void setCondimentos(HashMap<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		
	}

	public void setIngredientes(HashMap<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
		
	}

	public void setPasosPreparacion(HashMap<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		
	}

}
