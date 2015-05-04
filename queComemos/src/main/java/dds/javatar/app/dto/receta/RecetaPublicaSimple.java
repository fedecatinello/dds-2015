package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map.Entry;

import dds.javatar.app.dto.usuario.Usuario;

public class RecetaPublicaSimple extends RecetaSimple implements RecetaPublica{

	/**** builders ****/
	public RecetaPublicaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
	}

	public RecetaPublicaSimple(Integer calorias) {
		this();
		this.calorias = calorias;
	}
	
	public Receta clone() {
		RecetaPublicaSimple recetaClonada = new RecetaPublicaSimple();
		recetaClonada.nombre = this.nombre;
		recetaClonada.preparacion = this.preparacion;
		recetaClonada.calorias = this.calorias;

		recetaClonada.dificultad = this.dificultad;
		recetaClonada.temporada = this.temporada;
		// TODO autor..

		for (Entry<String, BigDecimal> entry : this.ingredientes.entrySet()) {
			recetaClonada.ingredientes.put(entry.getKey(), entry.getValue());
		}
		for (Entry<String, BigDecimal> entry : this.condimentos.entrySet()) {
			recetaClonada.condimentos.put(entry.getKey(), entry.getValue());
		}
		/*
		for (Receta subReceta : this.subRecetas) {
			recetaClonada.agregarSubReceta(subReceta.clone());
		}*/
		
		return recetaClonada;
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		return true;
	}
	
	public Boolean chequearModificacion(Receta receta, Usuario usuario) {
		return true;
	}
		
}
