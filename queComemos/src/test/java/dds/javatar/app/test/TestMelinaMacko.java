package dds.javatar.app.test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.Assert;

import org.junit.Test;

import dds.javatar.app.dto.Usuario;



public class TestMelinaMacko {

	@Test
	public void IMC(){
		Usuario meli = new Usuario();
		MathContext mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
		BigDecimal altura = new BigDecimal(1.47);
		BigDecimal peso = new BigDecimal(42);
		meli.setAltura(altura);
		meli.setPeso(peso);
		BigDecimal actual = meli.getIMC(mc.getPrecision());
		Assert.assertTrue(actual.toString().equals("19.43635"));
		System.out.println("Mi peso calculado por mi calculadora es:19.43635, el peso"
				+ " calculado por la app es:" +actual.toString());
		
		
	}
	
}
