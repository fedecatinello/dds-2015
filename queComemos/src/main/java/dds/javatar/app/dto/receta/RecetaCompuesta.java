package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import dds.javatar.app.util.BusinessException;

public class RecetaCompuesta implements Receta {
	private String nombre;
	private String preparacion;
	private Integer calorias;
	private String dificultad;
	private String temporada;
	private TipoReceta tipo;

	private Map<String, BigDecimal> ingredientes;
	private Map<String, BigDecimal> condimentos;
	private Set<Receta> subRecetas;

	/**** builders ****/
	public RecetaCompuesta() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.subRecetas = new HashSet<Receta>();

	}

	public RecetaCompuesta(Integer calorias) {
		this();
		this.calorias = calorias;
	}

	public RecetaCompuesta(Integer calorias, TipoReceta tipo){
		this(calorias);
		this.setTipo(tipo);
	}

	/**** Setters & Getters ****/
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPreparacion() {
		return this.preparacion;
	}
	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}

	public Integer getCalorias() {
		return this.calorias;
	}
	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}

	public String getDificultad() {
		return this.dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public String getTemporada() {
		return this.temporada;
	}
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}

	public Map<String, BigDecimal> getCondimentos() {
		return this.condimentos;
	}

	public Map<String, BigDecimal> getIngredientes() {
		return this.ingredientes;
	}

	public TipoReceta getTipo() {
		return tipo;
	}
	public void setTipo(TipoReceta tipo) {
		this.tipo = tipo;
	}

	/**** Metodos ****/
	public void validarSiLaRecetaEsValida() throws BusinessException {
		if (!this.subRecetas.isEmpty()) {
			for (Iterator<Receta> iterator = subRecetas.iterator(); iterator.hasNext();) {
				Receta receta = iterator.next();
				receta.validarSiLaRecetaEsValida();				
			}
		}
		if (this.ingredientes.isEmpty()) {
			throw new BusinessException("La receta no es valida ya que no tiene ingredientes!");
		}
		if (this.calorias < 10 || this.calorias > 5000) {
			throw new BusinessException("La receta no es valida por su cantidad de calorias!");
		}
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}

	public void agregarSubReceta(Receta subReceta) {
		this.subRecetas.add(subReceta);
	}

	public Boolean contieneIngrediente(String ingrediente) {
		//PEnsar bien este caso, no esta terminado

		Boolean contieneIngredienteX=false;		
		for (Iterator<Receta> iterator = subRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			receta.contieneIngrediente(ingrediente);			
		}
		// TODO: habria que chequear en las subrecetas?
		return contieneIngredienteX;
	}

	public Boolean contieneCondimento(String condimento) {
		// TODO: habria que chequear en las subrecetas?
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		// TODO: habria que chequear en las subrecetas?

		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}

		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	@Override
	public Receta clone() {
		RecetaCompuesta recetaClonada = new RecetaCompuesta();
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

		for (Receta subReceta : this.subRecetas) {
			recetaClonada.agregarSubReceta(subReceta.clone());
		}

		return recetaClonada;
	}

	@SuppressWarnings("unchecked")
	public void actualizarDatos(Object[] modifs) {
		if (modifs[0] != null)
			this.setNombre((String) modifs[0]);
		if (modifs[1] != null)
			this.ingredientes = (Map<String, BigDecimal>) modifs[1];
		if (modifs[2] != null)
			this.condimentos = (Map<String, BigDecimal>) modifs[2];
		if (modifs[3] != null)
			this.setPreparacion((String) modifs[3]);
		if (modifs[4] != null)
			this.setCalorias((Integer) modifs[4]);
		if (modifs[5] != null)
			this.setDificultad((String) modifs[5]);
		if (modifs[6] != null)
			this.setTemporada((String) modifs[6]);

	}



}
