package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;

public abstract class TestGeneralAbstract {


	protected Usuario crearUsuarioBasicoValido() {
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

		return usuario;
	}

	protected RecetaPrivadaSimple crearRecetaPrivadaSimple(){
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}
	
	protected RecetaPrivadaSimple crearRecetaPrivadaSimpleConMuchasCalorias(){
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		ravioles.setCalorias(900);
		return ravioles;
	}

	protected RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta() throws RecetaException{

		RecetaPrivadaSimple condimentos = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure  = new RecetaPrivadaSimple(350);
		RecetaPrivadaSimple pollo  = new RecetaPrivadaSimple(220);
		RecetaPrivadaCompuesta polloConPure  = new RecetaPrivadaCompuesta();

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano",new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		pollo.agregarIngrediente("pollo", new BigDecimal(290));
		pollo.setNombre("pollo");

		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);
		polloConPure.setNombre("Pollo Con Pure");
		return polloConPure;
	}

	protected void crearListaRecetasParaUsuarioSize30(Usuario user) throws RecetaException{
		RecetaPrivadaCompuesta recetaPrivadaCompuesta;
		RecetaPrivadaSimple recetaPrivadaSimple;
		RecetaPrivadaSimple recetaPrivadaSimpleHipertenso; 
		for (int i = 0; i < 10; i++) {
			recetaPrivadaCompuesta =crearRecetaPrivadaCompuesta();
			recetaPrivadaSimple= crearRecetaPrivadaSimple();
			recetaPrivadaSimpleHipertenso = crearRecetaNoAptaParaHipertensos();
			
			user.agregarReceta(recetaPrivadaSimple);
			
			user.agregarReceta(recetaPrivadaCompuesta);
			
			user.agregarReceta(recetaPrivadaSimpleHipertenso);
		}

	}
	
	public RecetaPrivadaSimple crearRecetaNoAptaParaHipertensos() {
		RecetaPrivadaSimple pizza = new RecetaPrivadaSimple(350);
		pizza.setNombre("Pizza");
		pizza.agregarIngrediente("sal", new BigDecimal(300));
		pizza.agregarIngrediente("Agua", new BigDecimal(70));
		pizza.agregarIngrediente("Harina", new BigDecimal(100));
		return pizza;
	}
	

}