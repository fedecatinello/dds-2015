package dds.javatar.app.test;



import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.Usuario;



public class TestMelinaMacko {
	private Usuario user;
	private MathContext mc;
	
	@Before
	public void seteoUsuario(){
		user = new Usuario();
		BigDecimal altura = new BigDecimal(1.47);
		BigDecimal peso = new BigDecimal(42);
		this.user.setAltura(altura);
		this.user.setPeso(peso);
	}
	@Before
	public void seteo(){
		this.mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);	
	}
	

	@Test
	public void IMC(){
		BigDecimal expected = new BigDecimal(19.43635);
		BigDecimal actual = this.user.getIMC(mc.getPrecision());
		Assert.assertEquals(expected.doubleValue(), actual.doubleValue(), 0.1);
	}
	
}
