package dds.javatar.app.ui.controller;

import java.util.List;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class RecetasController {

	private JsonTransformer jsonTransformer;

	// private Gson gson;

	public RecetasController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		// this.gson = gson;
	}

	public void register() {

		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			response.status(400);
			response.body(ex.getMessage());
		});

		Spark.get("/recetasPublicas", "application/json;charset=utf-8", (request, response) -> {
			response.type("application/json;charset=utf-8");
			return RepositorioRecetas.getInstance().recetaConocidas;
		}, this.jsonTransformer);

		Spark.get("/recetas/:username", "application/json;charset=utf-8", (request, response) -> {

			Buscador buscador = new Buscador();
			String username = request.params(":username");
			Usuario usuarioLogueado;
			usuarioLogueado = RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre(username).build());

			List<Receta> recetas = buscador.realizarBusquedaPara(usuarioLogueado);

			if (!(usuarioLogueado.getFavoritos() == null || usuarioLogueado.getFavoritos().isEmpty())) {
				recetas = usuarioLogueado.getFavoritos();
			}

			response.type("application/json;charset=utf-8");
			return recetas;
		}, this.jsonTransformer);

	}
}
