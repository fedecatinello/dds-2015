package dds.javatar.app.ui.controller;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class UsuariosController {

	private JsonTransformer jsonTransformer;

	private Gson gson;

	public UsuariosController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		this.gson = gson;
	}

	public void register() {

		Spark.get("/recetasFavoritas/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario usuarioLogueado;
			usuarioLogueado = RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre(username).build());

			response.type("application/json;charset=utf-8");
			return usuarioLogueado.getFavoritos();
		}, this.jsonTransformer);

		Spark.get("/mensajeInicio/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario usuarioLogueado;
			usuarioLogueado = RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre(username).build());

			response.type("application/json;charset=utf-8");
			if (usuarioLogueado.getFavoritos() == null || usuarioLogueado.getFavoritos().isEmpty()) {
				return "Estas fueron tus úĺtimas consultas";
			} else {
				return "Estas son tus recetas favoritas";
			}
		}, this.jsonTransformer);

		Spark.post("/login", "application/json;charset=utf-8", (request, response) -> {

			String message = request.body();
			Usuario usuario = this.gson.fromJson(message, Usuario.class);

			response.type("application/json;charset=utf-8");
			
			/* Busco el usuario en el repositorio para validar */
			Usuario user = RepositorioUsuarios.getInstance().getByUsername(usuario);
	
			if(user != null)	
				response.status(200);				
			else 
				response.status(401);					
			
			return message;

		}, this.jsonTransformer);

		Spark.get("/profile/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");

			Usuario userExample = new Usuario.UsuarioBuilder().nombre(username).build();
			Usuario loggedUser = RepositorioUsuarios.getInstance().get(userExample);

			response.type("application/json;charset=utf-8");
			return loggedUser;
		}, this.jsonTransformer);
	}
	
}

