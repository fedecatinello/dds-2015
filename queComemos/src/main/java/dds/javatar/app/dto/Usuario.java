package dds.javatar.app.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
	
	public Usuario(){
		
	}
	
	public Usuario(BigDecimal altura, BigDecimal peso){
		this.altura = altura;
		this.peso = peso;
	}
	
	public BigDecimal getIMC(int presicion) {
		
		MathContext mc = new MathContext(presicion, RoundingMode.HALF_DOWN);
		BigDecimal cuadrado = altura.pow(2, mc);
		return peso.divide(cuadrado, mc); 
	}
}
