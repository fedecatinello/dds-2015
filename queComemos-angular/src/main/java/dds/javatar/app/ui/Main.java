package dds.javatar.app.ui;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.ui.controller.RecetasController;
import dds.javatar.app.ui.controller.UsuariosController;
import dds.javatar.app.ui.controller.util.ContainerFactory;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class Main {

	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonTransformer jsonTransformer = new JsonTransformer(gson);

		Spark.port(9000);
		Spark.staticFileLocation("/webapp");

		ContainerFactory.getInstance().agregarRecetasAlRepositorio();
		ContainerFactory.getInstance().agregarUsuariosAlRepo();
		new UsuariosController(jsonTransformer, gson).register();
		new RecetasController(jsonTransformer, gson).register();
		
	}
}
