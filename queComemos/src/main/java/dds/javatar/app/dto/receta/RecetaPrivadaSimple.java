package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map.Entry;

public class RecetaPrivadaSimple extends Receta implements RecetaPrivada {

	/**** builders ****/
	public RecetaPrivadaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
	}

	public RecetaPrivadaSimple(Integer calorias) {
		this();
		this.calorias = calorias;
	}
	
	public Receta clone() {
		RecetaPrivadaSimple recetaClonada = new RecetaPrivadaSimple();
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

	
}
