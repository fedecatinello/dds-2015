package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

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

	protected RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}

	protected RecetaPrivadaSimple crearRecetaPrivadaSimpleConMuchasCalorias() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		ravioles.setCalorias(900);
		return ravioles;
	}

	protected RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta() throws BusinessException {

		RecetaPrivadaSimple condimentos = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure = new RecetaPrivadaSimple(350);
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(220);
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano", new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		pollo.agregarIngrediente("pollo", new BigDecimal(290));
		pollo.setNombre("pollo");

		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);
		return polloConPure;
	}

	protected void crearListaRecetasParaUsuarioSize20(Usuario user) throws BusinessException {
		RecetaPrivadaCompuesta recetaPrivadaCompuesta;
		RecetaPrivadaSimple recetaPrivadaSimple;
		for (int i = 0; i < 10; i++) {
			recetaPrivadaCompuesta = this.crearRecetaPrivadaCompuesta();
			recetaPrivadaSimple = this.crearRecetaPrivadaSimple();

			user.agregarReceta(recetaPrivadaSimple);

			user.agregarReceta(recetaPrivadaCompuesta);
		}
	}

}
