package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
			List<Componente> ingredientes,
			List<Componente> condimentos,
			List<Paso> pasosPreparacion) 
	{
		this.nombre = nombre;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.ingredientes = new ArrayList<Componente>();
		this.ingredientes.addAll(ingredientes);
		this.condimentos = new ArrayList<Componente>();
		this.condimentos.addAll(condimentos);
		this.pasosPreparacion = new ArrayList<Paso>();
		this.pasosPreparacion.addAll(pasosPreparacion);
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
	
	public void setCondimentos(List<Componente> condimentos) {
		this.condimentos = condimentos;
		
	}

	public void setIngredientes(List<Componente> ingredientes) {
		this.ingredientes = ingredientes;
		
	}

	public void setPasosPreparacion(List<Paso> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		
	}

}
