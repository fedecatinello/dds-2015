package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.CondicionPreexistente;
import dds.javatar.app.dto.usuario.Diabetico;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Vegano;
import dds.javatar.app.util.BusinessException;

public class TestSistema {

	private Usuario usuario;
	private Sistema sistema = Sistema.getInstance(); 

	@Before
	public void initialize() {
		this.usuario = this.crearUsuarioBasicoValido();
	}

	private Usuario crearUsuarioBasicoValido() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		Usuario usuario = new Usuario();
		usuario.setFechaNacimiento(calendar.getTime());
		usuario.setNombre("Nombre del usuario");
		usuario.setSexo(Usuario.Sexo.MASCULINO);
		usuario.setPeso(new BigDecimal(70));
		usuario.setAltura(new BigDecimal(1.77));
		usuario.setRutina(new Rutina(TipoRutina.FUERTE, 20));
		Map<String, Boolean> alimentosQueLeDisgustan = new HashMap<String, Boolean>();
		alimentosQueLeDisgustan.put("zanahoria", true);
		alimentosQueLeDisgustan.put("inojo", false);
		alimentosQueLeDisgustan.put("almejas", true);
		alimentosQueLeDisgustan.put("risoto", true);
		usuario.setAlimentosQueLeDisgustan(alimentosQueLeDisgustan );

		return usuario;
	}
	private RecetaPrivadaSimple crearRecetaPrivadaSimple(){
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}
	private RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta() throws BusinessException{

		RecetaPrivadaSimple condimentos = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure  = new RecetaPrivadaSimple(350);
		RecetaPrivadaSimple pollo  = new RecetaPrivadaSimple(220);
		RecetaPrivadaCompuesta polloConPure  = new RecetaPrivadaCompuesta();

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano",new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		pollo.agregarIngrediente("pollo", new BigDecimal(280));
		pollo.setNombre("pollo");

		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);
		return polloConPure;
	}
	
	private Receta crearRecetaPublicaSimpleRica() {
		RecetaPublicaSimple nioquis = new RecetaPublicaSimple(350);
		nioquis.setNombre("Nioquis");
		nioquis.agregarIngrediente("Harina", new BigDecimal(300));
		nioquis.agregarIngrediente("Agua", new BigDecimal(70));
		nioquis.agregarIngrediente("Verdura", new BigDecimal(100));
		return nioquis;
	}

	@Test
	public void unaRecetaQueLeGustaPuedeSugerirseAUnUsuario() throws BusinessException {
		this.sistema.sugerir(crearRecetaPublicaSimpleRica(), crearUsuarioBasicoValido());
		
	}
	
	
	//un teste con una receta que no le gusta
	//un test con una receta que no sea de su perfil
	// un teste con una receta compuesta que no le guta
	// un test con una receta compuesta que le gusta
	//un test con una receta compuesta que no este en su perfil
	//recetas que conoce 
	
	
}
