
package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;
import dds.javatar.app.dto.Usuario;

public class TestEliana {

	Usuario eliana = new Usuario(new BigDecimal(1.66), new BigDecimal(62.00)); 
	@Test
	public void testGetIMC() {
		MathContext mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
		BigDecimal expected = new BigDecimal(22.49964, mc);
		System.out.println("Se espera: "+expected.toString());
		System.out.println("Se obtiene: "+eliana.getIMC(mc.getPrecision()));
		assertTrue(expected.equals(eliana.getIMC(mc.getPrecision())));
	}

}
