package dds.javatar.app.dto.receta.busqueda.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.sistema.Solicitud;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.EstadoSolicitud;
import dds.javatar.app.dto.usuario.Usuario.Sexo;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaPrivadaCompuestaBuilder implements RecetaBuilder {

	private RecetaPrivadaCompuesta recetaPrivadaCompuesta;
	
	public RecetaPrivadaCompuestaBuilder(String nombre) {
		recetaPrivadaCompuesta = new RecetaPrivadaCompuesta();
		recetaPrivadaCompuesta.setNombre(nombre);
	}
	
	public RecetaPrivadaCompuestaBuilder inventadaPor(String autor) {
		recetaPrivadaCompuesta.setAutor(autor);
		return this;
	}
	
	public RecetaPrivadaCompuestaBuilder dificultad(String dificultad) {
		recetaPrivadaCompuesta.setDificultad(dificultad);
		return this;
	}
	
	public RecetaPrivadaCompuestaBuilder totalCalorias(Integer calorias) {
		recetaPrivadaCompuesta.setCalorias(calorias);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder temporada(String temporada) {
		recetaPrivadaCompuesta.setTemporada(temporada);
		return this;
	}
	
	public RecetaPrivadaCompuestaBuilder agregarSubReceta(RecetaPrivada subReceta) throws RecetaException {
		recetaPrivadaCompuesta.agregarSubReceta(subReceta);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarCondimentos(String condimento, BigDecimal cantidad) {
		recetaPrivadaCompuesta.agregarCondimento(condimento, cantidad);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		recetaPrivadaCompuesta.agregarIngrediente(ingrediente, cantidad);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		recetaPrivadaCompuesta.agregarPasoPreparacion(nroPaso, pasoPreparacion);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		recetaPrivadaCompuesta.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public Receta buildReceta() {
		return recetaPrivadaCompuesta;
	}

}
