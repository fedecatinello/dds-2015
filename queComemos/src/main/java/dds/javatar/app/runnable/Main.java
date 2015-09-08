package dds.javatar.app.runnable;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.receta.util.transformer.JsonTransformer;
import dds.javatar.app.dto.receta.controller.RecetaController;

public class Main {

	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonTransformer jsonTransformer = new JsonTransformer(gson);

		Spark.port(9000);
		Spark.staticFileLocation("/webapp");
		
		new RecetaController(jsonTransformer).register();
	}
}