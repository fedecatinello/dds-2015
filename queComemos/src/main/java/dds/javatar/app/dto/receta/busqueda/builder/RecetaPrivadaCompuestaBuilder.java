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

public class RecetaPrivadaCompuestaBuilder implements StrategyBuilderReceta {
	private HashSet<RecetaPrivada> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;
	private Map<Integer, String> pasosPreparacion;
	private String nombre;
	private String dificultad;
	private Integer calorias;
	private String temporada;
	private Integer tiempoPreparacion;

	/**** Setters y getters ****/
	public RecetaPrivadaCompuestaBuilder subRecetas(HashSet<RecetaPrivada> subRecetas) {
		this.subRecetas = subRecetas;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder condimentos(Map<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder ingredientes(Map<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder pasosPreparacion(Map<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public RecetaPrivadaCompuestaBuilder dificultad(String dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder calorias(Integer calorias) {
		this.calorias = calorias;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder temporada(String temporada) {
		this.temporada = temporada;
		return this;
	}

	public RecetaPrivadaCompuestaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
		return this;
	}


	@Override
	public Receta buildReceta() {
		return new RecetaPrivadaCompuesta.RecetaPrivadaCompuestaBuilder().nombre(this.nombre).subRecetas(this.subRecetas).condimentos(this.condimentos).ingredientes(this.ingredientes).pasosPreparacion(this.pasosPreparacion).dificultad(this.dificultad).calorias(this.calorias).temporada(this.temporada).tiempoPreparacion(this.tiempoPreparacion).build();

	}

}
