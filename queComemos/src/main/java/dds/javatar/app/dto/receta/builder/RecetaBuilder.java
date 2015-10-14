package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.Paso;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaCompuesta;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaBuilder {

	private String nombre;
	private String dificultad;
	private Integer calorias;
	private String temporada;
	private Integer tiempoPreparacion;
	private String autor;
	private Integer anioCreacion;
	private List<Componente> condimentos;
	private List<Componente> ingredientes;
	private List<Paso> pasosPreparacion;
	private Set<Receta> subrecetas;

	public RecetaBuilder(String nombre) {
		this.nombre = nombre;
		this.initializeComparatorFields();
		this.condimentos = new ArrayList<Componente>();
		this.ingredientes = new ArrayList<Componente>();
		this.pasosPreparacion = new ArrayList<Paso>();
		this.subrecetas = new HashSet<Receta>();
	}

	public void initializeComparatorFields() {
		/** To avoid null pointer exception in comparisons **/
		this.autor = new String();
		this.calorias = 0;
	}

	/** Builder methods **/
	public RecetaBuilder dificultad(String dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaBuilder totalCalorias(Integer calorias) {
		this.calorias = calorias;
		return this;
	}

	public RecetaBuilder temporada(String temporada) {
		this.temporada = temporada;
		return this;
	}

	public RecetaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
		return this;
	}

	public RecetaBuilder inventadaPor(String autor) {
		this.autor = autor;
		return this;
	}

	public RecetaBuilder inventadaEn(Integer anio) {
		this.anioCreacion = anio;
		return this;
	}

	public RecetaBuilder agregarCondimento(String condimento, BigDecimal cantidad) {
		Componente _condimento = new Componente(condimento, cantidad);
		this.condimentos.add(_condimento);
		return this;
	}

	public RecetaBuilder agregarCondimentos(List<Componente> condimentos) {
		this.condimentos.addAll(condimentos);
		return this;
	}

	public RecetaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		Componente _ingrediente = new Componente(ingrediente, cantidad);
		this.ingredientes.add(_ingrediente);
		return this;
	}

	public RecetaBuilder agregarIngredientes(List<Componente> ingredientes) {
		this.ingredientes.addAll(ingredientes);
		return this;
	}

	public RecetaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		Paso paso = new Paso(nroPaso, pasoPreparacion);
		this.pasosPreparacion.add(paso);
		return this;
	}

	public RecetaBuilder agregarPasos(List<Paso> pasosPreparacion) {
		this.pasosPreparacion.addAll(pasosPreparacion);
		return this;
	}

	public RecetaBuilder agregarSubReceta(Receta subReceta) throws RecetaException {
		subReceta.validar();
		this.subrecetas.add(subReceta);
		return this;
	}

	public RecetaBuilder agregarSubRecetas(Set<Receta> subRecetas) throws RecetaException {
		this.validarSubrecetas(subRecetas);
		this.subrecetas.addAll(subRecetas);
		return this;
	}

	/** Util **/
	public void validarSubrecetas(Set<Receta> subRecetas) throws RecetaException {

		Iterator<Receta> iteratorRecetas = subRecetas.iterator();

		while (iteratorRecetas.hasNext()) {
			Receta receta = iteratorRecetas.next();
			receta.validar();
		}
	}

	public Receta buildReceta() {

		if (this.autor.isEmpty()) {
			if (this.esCompuesta()) {
				return new RecetaCompuesta(this.nombre, this.calorias, this.dificultad, this.temporada, this.ingredientes, this.condimentos,
						this.pasosPreparacion, this.subrecetas);
			} else {
				return new Receta(this.nombre, this.calorias, this.dificultad, this.temporada, this.ingredientes, this.condimentos, this.pasosPreparacion);
			}
		} else {
			if (this.esCompuesta()) {
				return new RecetaCompuesta(this.nombre, this.autor, this.calorias, this.dificultad, this.temporada, this.condimentos, this.ingredientes,
						this.pasosPreparacion, this.subrecetas);
			} else {
				return new Receta(this.nombre, this.autor, this.calorias, this.dificultad, this.temporada, this.ingredientes, this.condimentos,
						this.pasosPreparacion);
			}
		}
	}

	public Boolean esCompuesta() {
		return (!this.subrecetas.isEmpty());
	}

}
