package dds.javatar.app.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Usuario {

	private BigDecimal altura;
	private BigDecimal peso;
	
	/* Setters y getters */
	
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
	
	/* Constructors */
	
	public Usuario(){
		
	}
	
	public Usuario(BigDecimal altura, BigDecimal peso){
		this.altura = altura;
		this.peso = peso;
	}
	
	/* Obtener la masa corporal dada una presicion */
	
	public BigDecimal getIMC(int precision) {
		
		MathContext mc = new MathContext(precision, RoundingMode.HALF_DOWN);
		BigDecimal cuadrado = this.altura.pow(2, mc);
		return this.peso.divide(cuadrado, mc); 
	}
}
