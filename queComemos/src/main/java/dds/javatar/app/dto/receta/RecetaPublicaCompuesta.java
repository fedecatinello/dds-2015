package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta.RecetaPrivadaCompuestaBuilder;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPublicaCompuesta implements RecetaPublica {

	private HashSet<RecetaPublica> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;
	private Map<Integer, String> pasosPreparacion;
	private String nombre;
	private String dificultad;
	private Integer calorias;
	private String temporada;
	private Integer tiempoPreparacion;

//	/** Builder **/
//	public RecetaPublicaCompuesta() {
//		this.subRecetas = new HashSet<RecetaPublica>();
//		this.agregarRecetaAlRepo(this);
//	}
	
	/** Builder **/

	public RecetaPublicaCompuesta(RecetaPublicaCompuestaBuilder recetaPUblicaCompuestaBuilder) {
		this.nombre = recetaPUblicaCompuestaBuilder.nombre;
		this.subRecetas = recetaPUblicaCompuestaBuilder.subRecetas;
		this.condimentos = recetaPUblicaCompuestaBuilder.condimentos;
		this.ingredientes = recetaPUblicaCompuestaBuilder.ingredientes;
		this.pasosPreparacion = recetaPUblicaCompuestaBuilder.pasosPreparacion;
		this.dificultad = recetaPUblicaCompuestaBuilder.dificultad;

		this.temporada = recetaPUblicaCompuestaBuilder.temporada;		
		this.tiempoPreparacion = recetaPUblicaCompuestaBuilder.tiempoPreparacion;
		
	}

	public static class RecetaPublicaCompuestaBuilder{
		private HashSet<RecetaPrivada> subRecetas;
		private Map<String, BigDecimal> condimentos;
		private Map<String, BigDecimal> ingredientes;
		private Map<Integer, String> pasosPreparacion;
		private String nombre;
		private String dificultad;
		private Integer calorias;
		private String temporada;
		private Integer tiempoPreparacion;
				
		public RecetaPublicaCompuestaBuilder subRecetas(HashSet<RecetaPrivada> subRecetas) {
			this.subRecetas = subRecetas;
			return this;
		}

		public RecetaPublicaCompuestaBuilder condimentos(Map<String, BigDecimal> condimentos) {
			this.condimentos = condimentos;
			return this;
		}

		public RecetaPublicaCompuestaBuilder ingredientes(Map<String, BigDecimal> ingredientes) {
			this.ingredientes = ingredientes;
			return this;
		}

		public RecetaPublicaCompuestaBuilder pasosPreparacion(Map<Integer, String> pasosPreparacion) {
			this.pasosPreparacion = pasosPreparacion;
			return this;
		}

		public RecetaPublicaCompuestaBuilder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}
		
		public RecetaPublicaCompuestaBuilder dificultad(String dificultad) {
			this.dificultad = dificultad;
			return this;
		}

		public RecetaPublicaCompuestaBuilder calorias(Integer calorias) {
			this.calorias = calorias;
			return this;
		}

		public RecetaPublicaCompuestaBuilder temporada(String temporada) {
			this.temporada = temporada;
			return this;
		}

		public RecetaPublicaCompuestaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
			this.tiempoPreparacion = tiempoPreparacion;
			return this;
		}
		
		public Receta build() {
			return new RecetaPublicaCompuesta(this);
		}
		
	}
	
	/** get items **/
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.nombre = temporada;
	}
	
	public String getDificultad() {
		return this.dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public Integer getCalorias() {
		int caloriasTotal = 0;
		for (Iterator<RecetaPublica> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			caloriasTotal = caloriasTotal + receta.getCalorias();
		}
		return caloriasTotal;
	}
	

	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}


	public Set<RecetaPublica> getSubRecetas() {
		return this.subRecetas;
	}

	public Map<String, BigDecimal> getCondimentos() {
		for (Iterator<RecetaPublica> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.condimentos.putAll(receta.getCondimentos());
		}
		return condimentos;
	}

	public void setCondimentos(Map<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		
	}

	public Map<String, BigDecimal> getIngredientes() {
		for (Iterator<RecetaPublica> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.ingredientes.putAll(receta.getIngredientes());
		}
		return ingredientes;
	}


	public void setIngredientes(Map<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;		
	}
	
	public Map<Integer, String> getPasosPreparacion() {
		for (Iterator<RecetaPublica> iterator = subRecetas.iterator(); iterator
				.hasNext();) {
			Receta receta = (Receta) iterator.next();
			this.pasosPreparacion.putAll(receta.getPasosPreparacion());
		}
		return pasosPreparacion;
	}

	public void setPasosPreparacion(Map<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;		
	}
		
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	/** Validadores **/
	public Boolean contieneIngrediente(String ingrediente) {
		this.getIngredientes();
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		this.getCondimentos();
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento,
			BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}


	@Override
	public void validarSiLaRecetaEsValida() throws RecetaException {
		if (this.subRecetas.isEmpty()) {
			throw new RecetaException(
					"La receta no es valida ya que esta vacia! (No tiene subrecetas)");
		}
	}

	/** Add items **/
	public void agregarSubReceta(RecetaPublica subReceta)
			throws RecetaException {
		subReceta.validarSiLaRecetaEsValida();
		this.subRecetas.add(subReceta);
	}

	@Override
	public void agregarRecetaAlRepo(RecetaPublica receta) {
		RepositorioRecetas.getInstance().agregar(receta);
	}

	public Receta privatizarSiCorresponde (Usuario user) throws UsuarioException, RecetaException{
		RecetaPrivadaCompuesta recetaClonada = new RecetaPrivadaCompuesta();
		recetaClonada.nombre = this.nombre;
		recetaClonada.dificultad = this.dificultad;
		recetaClonada.temporada = this.temporada;
			
		for (RecetaPublica receta : this.subRecetas) {
			recetaClonada.agregarSubReceta((RecetaPrivada) receta.privatizarSiCorresponde(user));
		}
		
		user.agregarReceta(recetaClonada);
		user.quitarReceta(this);
		return recetaClonada;
		
	}

		
}
