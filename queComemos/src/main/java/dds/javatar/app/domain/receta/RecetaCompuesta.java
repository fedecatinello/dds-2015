package dds.javatar.app.domain.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.domain.receta.builder.RecetaBuilder;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.domain.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaCompuesta extends Receta {

	private Set<Receta> subRecetas;

	protected Set<CondicionPreexistente> condiciones;

	public RecetaCompuesta(String nombre, Integer calorias, String dificultad, String temporada, Map<String, BigDecimal> ingredientes,
			Map<String, BigDecimal> condimentos, Map<Integer, String> pasosPreparacion, Set<Receta> subRecetas, Integer anioCreacion, Integer tiempoPreparacion) {
		this(nombre, null, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion, subRecetas, anioCreacion, tiempoPreparacion);
	}

	public RecetaCompuesta(String nombre, String autor, Integer calorias, String dificultad, String temporada, Map<String, BigDecimal> ingredientes,
			Map<String, BigDecimal> condimentos, Map<Integer, String> pasosPreparacion, Set<Receta> subRecetas, Integer anioCreacion, Integer tiempoPreparacion) {
		super(nombre, autor, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion, anioCreacion, tiempoPreparacion);
		this.subRecetas = subRecetas;
	}

	/* Getters & Setters */

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}

	public void setSubRecetas(Set<Receta> subRecetas) {
		this.subRecetas = subRecetas;
	}

	@Override
	public Set<CondicionPreexistente> getCondiciones() {
		return this.condiciones;
	}

	@Override
	public void setCondiciones(Set<CondicionPreexistente> condiciones) {
		this.condiciones = condiciones;
	}

	public void agregarSubReceta(Receta subReceta) throws RecetaException {
		this.subRecetas.add(subReceta);
	}

	@Override
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}

	@Override
	public Receta privatizarSiCorresponde(Usuario user) throws UsuarioException, RecetaException {

		HashSet<Receta> recetasPrivatizadas = new HashSet<Receta>();

		for (Receta receta : this.subRecetas) {
			recetasPrivatizadas.add(receta.privatizarSiCorresponde(user));
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
	public Receta hacerPrivada(String autor) throws RecetaException {
		Receta recetaClonada = new RecetaBuilder(this.nombre)
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

	@Override
	public void validar() throws RecetaException {
		super.validar();
		if (this.subRecetas.isEmpty()) {
			throw new RecetaException("La receta no es valida ya que esta vacia! (No tiene subrecetas)");
		}
	}

}