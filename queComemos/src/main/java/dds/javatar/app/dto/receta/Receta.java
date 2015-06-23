package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.Map;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public interface Receta {
	
	//Getters y Setters
	public String getNombre();
	public void setNombre(String nombre);
	
	public String getTemporada();
	public void setTemporada(String temporada);

	public Integer getCalorias();
	public void setCalorias(Integer calorias);
	
	public String getDificultad();
	public void setDificultad(String dificultad);
	
	public Integer getTiempoPreparacion();
	public void setTiempoPreparacion(Integer tiempoPreparacion);
	
	public Map<String, BigDecimal> getCondimentos();
	public void setCondimentos(Map<String, BigDecimal> condimentos);
	
	public Map<String, BigDecimal> getIngredientes();
	public void setIngredientes(Map<String, BigDecimal> ingredientes);
	
	public Map<Integer, String> getPasosPreparacion();
	public void setPasosPreparacion(Map<Integer, String> pasosPreparacion);
	
	
	public Boolean contieneIngrediente(String ingrediente) ;
	public Boolean contieneCondimento(String condimento);
	public void validarSiLaRecetaEsValida() throws RecetaException;
	
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad);
	
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario);
	public Receta privatizarSiCorresponde (Usuario usuario) throws UsuarioException, RecetaException;
		
}
