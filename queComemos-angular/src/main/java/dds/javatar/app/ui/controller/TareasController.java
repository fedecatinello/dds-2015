package dds.javatar.app.ui.controller;

import java.math.BigDecimal;
import java.util.List;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class TareasController {

	private JsonTransformer jsonTransformer;
	private Gson gson;

	public TareasController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		this.gson = gson;
	}

	public void register() {

		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			response.status(400);
			response.body(ex.getMessage());
		});

		Spark.get("/recetas", (request, response) -> {
		
				Usuario usuario = new Usuario.UsuarioBuilder()
					.nombre("DonJuan")
						.sexo(Usuario.Sexo.MASCULINO)
						.peso(new BigDecimal(70))
						.altura(new BigDecimal(1.77))
						.rutina(new Rutina(TipoRutina.FUERTE, 20))
						.build();

				Buscador buscador = new Buscador();
				List<Receta> recetas = buscador.realizarBusquedaPara(usuario);
				response.type("application/json;charset=utf-8");
				return recetas;
			}, this.jsonTransformer);
		
		
	}
}
