package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.util.BusinessException;

public interface Receta {

	public String getNombre();
	public void setNombre(String nombre);
	
	public String getPreparacion();
	public void setPreparacion(String preparacion);
	
	public Integer getCalorias();
	public void setCalorias(Integer calorias);
	
	public String getDificultad();
	public void setDificultad(String dificultad);
	
	public String getTemporada();
	public void setTemporada(String temporada);

	public TipoReceta getTipo();
	public void setTipo(TipoReceta tipo);
	
	public Set<Receta> getSubRecetas();
	public Map<String, BigDecimal> getCondimentos();
	public Map<String, BigDecimal> getIngredientes();

	
	public void validarSiLaRecetaEsValida() throws BusinessException;
	public void agregarIngrediente(String ingrediente, BigDecimal cantidad);
	public void agregarSubReceta(Receta subReceta) ;
	public Boolean contieneIngrediente(String ingrediente);
	public Boolean contieneCondimento(String condimento);
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) ;
	public Receta clone();
	public void actualizarDatos(Object[] modifs) ;

}
