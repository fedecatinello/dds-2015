package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.Usuario;

public class TestUsuario {

	private MathContext mc;
	
	@Before
	public void initialize() {
		this.mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
	}
	
	private void assertIMC(Usuario usuario, double expectedValue) {
		BigDecimal expected = new BigDecimal(expectedValue, mc);
		assertEquals(expected.doubleValue(), usuario.getIMC(mc.getPrecision()).doubleValue(), 0.1);
	}
	
	@Test
	public final void testPabloGomez() {
		Usuario usuario = new Usuario(new BigDecimal(1.75), new BigDecimal(65.0));
		this.assertIMC(usuario, 21.2244898);
	}

	@Test
	public void testMelinaMacko(){
		Usuario meli = new Usuario();
		BigDecimal altura = new BigDecimal(1.47);
		BigDecimal peso = new BigDecimal(42);
		meli.setAltura(altura);
		meli.setPeso(peso);
		this.assertIMC(meli,19.43635);
	}
	
	@Test
	public final void testFedericoCatinello() {
		Usuario fede = new Usuario(new BigDecimal(1.72), new BigDecimal(75));
		this.assertIMC(fede, 25.35154137);
	}
	
	@Test
	public final void testElianaLuguerosSinatra() {
		Usuario eliana = new Usuario(new BigDecimal(1.66), new BigDecimal(62));
		this.assertIMC(eliana, 22.49964);
	}
}
