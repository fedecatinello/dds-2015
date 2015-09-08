package dds.javatar.app.dto.receta.controller;

import java.util.List;

import dds.javatar.app.dto.receta.util.transformer.JsonTransformer;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.receta.Receta;
import spark.Spark;

public class RecetaController {

	private JsonTransformer transformerReceta;
	
	public RecetaController(JsonTransformer jsonTransformer) {
		this.transformerReceta = jsonTransformer;
	}
	
	public void register() {

		Spark.get("/recetas", (request, response) -> {
			List<Receta> recetas = RepositorioRecetas.getInstance().listarTodas();
			response.type("application/json;charset=utf-8");
			return recetas;
		}, this.transformerReceta);
	}
}
