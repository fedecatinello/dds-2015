package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.Map;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public abstract class RecetaSimple implements Receta{

	protected String nombre;
	protected String preparacion;
	protected Integer calorias;
	protected String dificultad;
	protected String temporada;
	
	protected Map<String, BigDecimal> ingredientes;
	protected Map<String, BigDecimal> condimentos;


	
	/**		Getters & Setters			**/
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

	public void agregarCondimento(String condimento, BigDecimal cantidad) {
		this.condimentos.put(condimento, cantidad);
	}
	public Map<String, BigDecimal> getCondimentos() {
		return this.condimentos;
	}
	
	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}
	public Map<String, BigDecimal> getIngredientes() {
		return this.ingredientes;
	}

	
	
	/**			Metodos			**/	
	public void validarSiLaRecetaEsValida() throws BusinessException {
		if (this.ingredientes.isEmpty()) {
			throw new BusinessException("La receta no es valida ya que no tiene ingredientes!");
		}
		if (this.calorias < 10 || this.calorias > 5000) {
			throw new BusinessException("La receta no es valida por su cantidad de calorias!");
		}
	}
	
	public Boolean contieneIngrediente(String ingrediente) {
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}
	
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if(usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}
	
	public Boolean chequearModificacion(Receta receta, Usuario usuario) {
		return receta.chequearVisibilidad(receta, usuario);
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
