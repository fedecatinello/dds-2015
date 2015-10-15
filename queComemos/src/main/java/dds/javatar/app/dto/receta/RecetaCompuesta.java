package dds.javatar.app.dto.receta;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaCompuesta extends Receta {

	/** Faltaria el tema de las recetas con las subrecetas **/
	private Set<Receta> subRecetas;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "receta_condicion", joinColumns = @JoinColumn(name = "receta_id"), inverseJoinColumns = @JoinColumn(name = "condicion_id"))
	protected Set<CondicionPreexistente> condiciones;

	public RecetaCompuesta(String nombre, Integer calorias, String dificultad, String temporada, Set<Componente> ingredientes, Set<Componente> condimentos,
			Set<Paso> pasosPreparacion, Set<Receta> subRecetas) {
		this(nombre, null, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion, subRecetas);
	}

	public RecetaCompuesta(String nombre, Usuario autor, Integer calorias, String dificultad, String temporada, Set<Componente> ingredientes,
			Set<Componente> condimentos, Set<Paso> pasosPreparacion, Set<Receta> subRecetas) {
		super(nombre, autor, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion);
		this.subRecetas = subRecetas;
	}

	/* Getters & Setters */

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}

	public void setSubRecetas(Set<Receta> subRecetas) {
		this.subRecetas = subRecetas;
	}

	public Set<CondicionPreexistente> getCondiciones() {
		return this.condiciones;
	}

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
