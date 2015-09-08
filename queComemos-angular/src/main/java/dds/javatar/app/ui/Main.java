package dds.javatar.app.ui;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.ui.controller.TareasController;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class Main {

	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonTransformer jsonTransformer = new JsonTransformer(gson);

		Spark.port(9000);
		Spark.staticFileLocation("/webapp");

		new TareasController(jsonTransformer, gson).register();
	}
}
