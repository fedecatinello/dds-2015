package dds.javatar.app.ui.test;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.receta.RecetaModel;

public class ViewRecetaTest {

	private RecetaModel model;

	@Before
	public void initialize() {
		this.model = new RecetaModel();

		Receta receta = new RecetaBuilder("Ravioles2")
			.totalCalorias(350)
			.agregarIngrediente("azucar", new BigDecimal(150))
			.agregarIngrediente("Harina", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Verdura", new BigDecimal(100))
			.agregarCondimento("Perejil", new BigDecimal(100))
			.inventadaPor("Santi")
			.temporada("Todo el año")
			.dificultad("Dificl")
			.buildReceta();

		HashMap<Integer, String> pasos = new HashMap<Integer, String>();
		pasos.put(1, "Agregar agua");
		pasos.put(3, "comer");
		pasos.put(2, "Agregar harina");
		receta.setPasosPreparacion(pasos);

		Usuario usuario = new Usuario.UsuarioBuilder()
			.nombre("ElSiscador")
			.sexo(Usuario.Sexo.MASCULINO)
			.peso(new BigDecimal(75))
			.altura(new BigDecimal(1.80))
			.rutina(new Rutina(TipoRutina.FUERTE, 20))
			.build();

		usuario.marcarFavorita(receta);

		this.model.setReceta(receta);
		this.model.setUsuarioLogeado(usuario);
	}

	@Test
	public void testDuenioDeReceta() {
		Assert.assertEquals(this.model.getDuenioDeReceta(), "receta publica");
	}

	@Test
	public void testPasosPreparacion() {
		Assert.assertEquals(this.model.getPasosPreparacion(), "Agregar agua. Agregar harina. comer");
	}

	@Test
	public void testEsFavorita() {
		Assert.assertTrue(this.model.getEsFavorita());
	}

	@Test
	public void testCondiciones() {
		Assert.assertEquals(this.model.getCondiciones().size(), 1);
		Assert.assertEquals(this.model.getCondiciones().get(0), "Diabetico");
	}

	@Test
	public void testCaloriasString() {
		Assert.assertEquals(this.model.getCaloriasString(), "350 calorias");
	}
}
