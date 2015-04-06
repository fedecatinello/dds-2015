package dds.javatar.app.dto;

import java.math.BigDecimal;

public class Usuario {

	private BigDecimal altura;
	private BigDecimal peso;
	
	public BigDecimal getAltura() {
		return altura;
	}
	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	
	public BigDecimal getIMC() {
		
		BigDecimal cuadrado = altura.pow(2);
		return peso.divide(cuadrado); 
	}
}
