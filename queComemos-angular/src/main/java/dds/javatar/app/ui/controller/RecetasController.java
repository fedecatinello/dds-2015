package dds.javatar.app.ui.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import queComemos.entrega3.dominio.Dificultad;
import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.Busqueda.BusquedaBuilder;
import dds.javatar.app.dto.receta.filtro.FiltroCondiciones;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class RecetasController {

	private JsonTransformer jsonTransformer;

	private Gson gson;

	public RecetasController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		this.gson = gson;
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

		Spark.get("/recetas/buscar", "application/json;charset=utf-8", (request, response) -> {
			Buscador buscador = new Buscador();

			String username = request.queryParams("username");
			String nombre = request.queryParams("nombre");
			String caloriasDesde = request.queryParams("calorias_desde");
			String caloriasHasta = request.queryParams("calorias_hasta");
			String dificultad = request.queryParams("dificultad");
			String temporada = request.queryParams("temporada");
			String ingrediente = request.queryParams("ingrediente");

			Usuario usuario = RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre(username).build());

			Busqueda busqueda = new BusquedaBuilder()
				.nombre(nombre)
				.dificultad(dificultad != null ? Dificultad.valueOf(dificultad) : null)
				.palabrasClave(Arrays.asList(ingrediente, temporada))
				.build();

			List<Receta> recetas = buscador.realizarBusquedaPara(usuario, busqueda);
			if (request.queryParams("aplicar_filtros_usuario") != null) {
				buscador.setFiltros(Arrays.asList(new FiltroCondiciones()));
				buscador.filtrar(usuario, recetas);
			}

			response.type("application/json;charset=utf-8");
			return recetas;
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

		Spark.post("/updateReceta/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			String message = request.body();
			Receta receta = this.gson.fromJson(message, RecetaPrivadaSimple.class);
			RepositorioRecetas.getInstance().updateReceta(receta);			
			Usuario userLogueado = RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre(username).build());
			userLogueado.updateFavorita(receta);
			response.status(200);
			return message;
		}, this.jsonTransformer);
		
		
		Spark.get("/ingredientes/:patron", "application/json;charset=utf-8", (request, response) -> {
			
			String patron = request.params(":patron");
			Set <String> ingredientes = RepositorioRecetas.getInstance().getAllIngredientes();
			return  ingredientes.removeIf(s -> !s.contains(patron));

		}, this.jsonTransformer);

	}
}
