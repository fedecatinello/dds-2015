package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.Map;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public interface Receta {
	public String getNombre();
	public void setNombre(String nombre);
	
	public String getTemporada();
	public void setTemporada(String temporada);

	
	public Map<String, BigDecimal> getCondimentos() ;
	public Map<String, BigDecimal> getIngredientes();
	public Map<Integer, String> getPasosPreparacion();
	
	public Boolean contieneIngrediente(String ingrediente) ;
	public Boolean contieneCondimento(String condimento);
	public Integer getCalorias();
	public void validarSiLaRecetaEsValida() throws RecetaException;
	
	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad);
	
	public Boolean chequearVisibilidad(Receta receta, Usuario usuario);
	public Receta privatizarSiCorresponde (Usuario usuario) throws UsuarioException, RecetaException;
		
}
