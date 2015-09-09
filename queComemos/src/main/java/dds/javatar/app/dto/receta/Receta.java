package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;

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
	
	public String getAutor();
	
	public Integer getTiempoPreparacion();
	public void setTiempoPreparacion(Integer tiempoPreparacion);
	
	public HashMap<String, BigDecimal> getCondimentos();
	public void setCondimentos(HashMap<String, BigDecimal> condimentos);
	
	public HashMap<String, BigDecimal> getIngredientes();
	public void setIngredientes(HashMap<String, BigDecimal> ingredientes);
	
	public HashMap<Integer, String> getPasosPreparacion();
	public void setPasosPreparacion(HashMap<Integer, String> pasosPreparacion);
	
	
	
	public Boolean contieneIngrediente(String ingrediente) ;
	public Boolean contieneCondimento(String condimento);
	public void validarSiLaRecetaEsValida() throws RecetaException;
	
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad);
	
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario);
	public Receta privatizarSiCorresponde (Usuario usuario) throws UsuarioException, RecetaException;
		
}
