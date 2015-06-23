package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPublicaSimple extends RecetaSimple implements RecetaPublica {

	/**** builders ****/
	public RecetaPublicaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.agregarRecetaAlRepo(this);
	}

	public RecetaPublicaSimple(Integer calorias) {
		this();
		this.calorias = calorias;
	}

	public Receta clonarme() {
		RecetaPrivadaSimple recetaClonada = new RecetaPrivadaSimple();
		recetaClonada.nombre = this.nombre;
		recetaClonada.calorias = this.calorias;
		recetaClonada.dificultad = this.dificultad;
		recetaClonada.temporada = this.temporada;
			
		recetaClonada.ingredientes.putAll(this.ingredientes);
		recetaClonada.condimentos.putAll(this.condimentos);
		recetaClonada.pasosPreparacion.putAll(this.pasosPreparacion);
		
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
		RecetaPrivadaSimple recetaClonada = new RecetaPrivadaSimple();
		recetaClonada.nombre = this.nombre;
		recetaClonada.calorias = this.calorias;
		recetaClonada.dificultad = this.dificultad;
		recetaClonada.temporada = this.temporada;
			
		recetaClonada.ingredientes.putAll(this.ingredientes);
		recetaClonada.condimentos.putAll(this.condimentos);
		recetaClonada.pasosPreparacion.putAll(this.pasosPreparacion);
		
		usuario.agregarReceta(recetaClonada);
		usuario.quitarReceta(this);
		return recetaClonada;
		
	}

	
	//Getters & Setters
	
	public void setCondimentos(Map<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		
	}

	public void setIngredientes(Map<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
		
	}

	public void setPasosPreparacion(Map<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		
	}

}
