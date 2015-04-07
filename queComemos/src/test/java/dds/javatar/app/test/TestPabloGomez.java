package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.Usuario;

public class TestPabloGomez {

	private Usuario usuario;
	
	@Before
	public void init() {
		this.usuario = new Usuario(new BigDecimal(1.75), new BigDecimal(65.0));
	}
	
	@Test
	public final void testGetIMC() {
		MathContext mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
		BigDecimal expected = new BigDecimal(21.2244898, mc);
		assertEquals(expected.doubleValue(), this.usuario.getIMC(mc.getPrecision()).doubleValue(), 0.1);
	}

}
