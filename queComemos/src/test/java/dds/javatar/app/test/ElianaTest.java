package dds.javatar.app.dto;

import java.math.BigDecimal;

public class ElianaTest {

	Usuario eliana = new Usuario(new BigDecimal(1.66), new BigDecimal(62.00));
	
	public final void testGetIMC() {

		MathContext mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
				BigDecimal expected = new BigDecimal(22.49963, mc);
				System.out.println("Se espera: "+expected.toString());
				System.out.println("Se obtiene: "+eliana.getIMC(mc.getPrecision()));
				assertTrue(expected.equals(eliana.getIMC(mc.getPrecision())));
	}
	
}
