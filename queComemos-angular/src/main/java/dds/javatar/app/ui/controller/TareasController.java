package dds.javatar.app.ui.controller;

import java.util.List;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
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
			List<Receta> recetas = RepositorioRecetas.getInstance().listarTodas();
			response.type("application/json;charset=utf-8");
			return recetas;
		}, this.jsonTransformer);
	}
}
