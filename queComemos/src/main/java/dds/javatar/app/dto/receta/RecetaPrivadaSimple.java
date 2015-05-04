package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;

public class RecetaPrivadaSimple extends Receta implements RecetaPrivada {

	/**** builders ****/
	public RecetaPrivadaSimple() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
	}

	public RecetaPrivadaSimple(Integer calorias) {
		this();
		this.calorias = calorias;
	}
		
}
