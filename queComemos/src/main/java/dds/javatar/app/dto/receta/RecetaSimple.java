package dds.javatar.app.dto.receta;

import java.math.BigDecimal;

import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaSimple extends AbstractReceta {

	/** Metodos **/
	public void validarSiLaRecetaEsValida() throws RecetaException {
		if (this.ingredientes.isEmpty()) {
			throw new RecetaException(
					"La receta no es valida ya que no tiene ingredientes!");
		}
		if (this.calorias.intValue() < 10 || this.calorias.intValue() > 5000) {
			throw new RecetaException(
					"La receta no es valida por su cantidad de calorias!");
		}
	}

	public Boolean contieneIngrediente(String ingrediente) {
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento,
			BigDecimal cantidad) {
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}
	

}
