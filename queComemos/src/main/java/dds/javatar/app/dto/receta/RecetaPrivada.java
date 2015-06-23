package dds.javatar.app.dto.receta;

import java.math.BigDecimal;

public interface RecetaPrivada extends Receta{
	
	public void agregarIngrediente(String ingrediente, BigDecimal cantidad);
	public void agregarPasoPreparacion(Integer nroPaso, String paso);
	public void agregarCondimento(String condimento, BigDecimal cantidad);
	
}
