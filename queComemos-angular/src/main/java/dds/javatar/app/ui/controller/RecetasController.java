package dds.javatar.app.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import queComemos.entrega3.dominio.Dificultad;
import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.busqueda.Buscador;
import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.receta.busqueda.Busqueda.BusquedaBuilder;
import dds.javatar.app.domain.receta.busqueda.adapter.BusquedaAdapter;
import dds.javatar.app.domain.receta.filtro.FiltroCondiciones;
import dds.javatar.app.domain.sistema.RepositorioRecetas;
import dds.javatar.app.domain.sistema.RepositorioUsuarios;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class RecetasController {

	private JsonTransformer jsonTransformer;
	private Integer consultas_receta;
	private Gson gson;

	public RecetasController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		this.gson = gson;
	}

	public void register() {

		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			ex.printStackTrace();
			response.body(ex.getMessage());
			Spark.halt(400);
		});

		Spark.get("/recetasPublicas", "application/json;charset=utf-8", (request, response) -> {
			response.type("application/json;charset=utf-8");
			return RepositorioRecetas.getInstance().listarTodas();
		}, this.jsonTransformer);

		Spark.get("/buscarRecetas", (request, response) -> {

			String username = request.queryParams("username");
			String nombre = request.queryParams("nombre");
			// Calorias No soportado por el buscador de la entrega 3 de uqbar:
			//String caloriasDesde = request.queryParams("calorias_desde");
			//String caloriasHasta = request.queryParams("calorias_hasta");
			String dificultad = request.queryParams("dificultad");
			String temporada = request.queryParams("temporada");
			String ingrediente = request.queryParams("ingrediente");

			Usuario usuario = RepositorioUsuarios.getInstance().getByUsername(username);

			List<String> palabrasClave = new ArrayList<>();
			if (ingrediente != null && !ingrediente.isEmpty()) {
				palabrasClave.add(ingrediente);
			}
			if (temporada != null && !temporada.isEmpty()) {
				palabrasClave.add(temporada);
			}

			Busqueda busqueda = new BusquedaBuilder()
				.nombre(nombre)
				.dificultad(dificultad != null ? Dificultad.valueOf(dificultad) : null)
				.palabrasClave(palabrasClave)
				.build();

			List<Receta> recetas = BusquedaAdapter.getInstance().consultarRecetas(usuario, busqueda);
			if (request.queryParams("aplicar_filtros_usuario") != null) {
				Buscador buscador = new Buscador();
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
			usuarioLogueado = RepositorioUsuarios.getInstance().getByUsername(username);

			List<Receta> recetas = buscador.realizarBusquedaPara(usuarioLogueado);

			if (!(usuarioLogueado.getFavoritos() == null || usuarioLogueado.getFavoritos().isEmpty())) {
				recetas = usuarioLogueado.getFavoritos();
			}

			response.type("application/json;charset=utf-8");
			return recetas;
		}, this.jsonTransformer);

		Spark.get("/recetasFavoritas/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario usuarioLogueado;
			usuarioLogueado = RepositorioUsuarios.getInstance().getByUsername(username);

			response.type("application/json;charset=utf-8");
			return usuarioLogueado.getFavoritos();
		}, this.jsonTransformer);

		Spark.post("/updateReceta/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			String message = request.body();
			Receta receta = this.gson.fromJson(message, Receta.class);
			RepositorioRecetas.getInstance().updateReceta(receta);
			Usuario userLogueado = RepositorioUsuarios.getInstance().getByUsername(username);
			userLogueado.updateFavorita(receta);
			response.status(200);
			return message;
		}, this.jsonTransformer);

		Spark.get("/ingredientes/:patron", "application/json;charset=utf-8", (request, response) -> {

			String patron = request.params(":patron");
			Set<String> ingredientes = RepositorioRecetas.getInstance().getAllIngredientes();
			return ingredientes.removeIf(s -> !s.contains(patron));

		}, this.jsonTransformer);
		
		Spark.get("/monitoreo/:receta", "application/json;charset=utf-8", (request, response) -> {

			String nombre_receta = request.params(":receta");
									
			BusquedaAdapter.getInstance().getObservers()
																	.forEach(observer -> this.consultas_receta = observer.cantidadConsultasReceta(nombre_receta));
			
			response.type("application/json;charset=utf-8");
			return this.consultas_receta;

		}, this.jsonTransformer);
		
		


	}
}
