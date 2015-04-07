package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

import dds.javatar.app.dto.Usuario;

public class TestFedericoCatinello {

	Usuario fcatinello = new Usuario(new BigDecimal(1.72),new BigDecimal(75.00));
	
	@Test
	public final void testGetIMC() {
		
		MathContext mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
		BigDecimal expected = new BigDecimal(25.35154137, mc);
		
		/* Imprimo valores para chequear igualdad */
		System.out.println(expected.toString());
		System.out.println(fcatinello.getIMC(mc.getPrecision()));
		
		assertEquals(expected.doubleValue(), fcatinello.getIMC(mc.getPrecision()).doubleValue(), 0.1);
	}

}
