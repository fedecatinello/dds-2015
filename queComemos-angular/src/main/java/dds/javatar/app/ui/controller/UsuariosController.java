package dds.javatar.app.ui.controller;

import java.math.BigDecimal;

import spark.Spark;

import com.google.gson.Gson;

import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.ui.controller.util.JsonTransformer;

public class UsuariosController {

	private JsonTransformer jsonTransformer;

	private Gson gson;

	public UsuariosController(JsonTransformer jsonTransformer, Gson gson) {
		this.jsonTransformer = jsonTransformer;
		this.gson = gson;
	}

	public void register() {
		
		Spark.exception(RuntimeException.class, (ex, request, response) -> {
			ex.printStackTrace();
			response.body(ex.getMessage());
			Spark.halt(400);
		});

		Spark.get("/mensajeInicio/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario usuarioLogueado;
			usuarioLogueado = RepositorioUsuarios.getInstance().getByUsername(username);

			response.type("application/json;charset=utf-8");
			if (usuarioLogueado.getFavoritos() == null || usuarioLogueado.getFavoritos().isEmpty()) {
				return "Estas fueron tus Ultimas consultas";
			} else {
				return "Estas son tus recetas favoritas";
			}
		}, this.jsonTransformer);

		Spark.post("/login", "application/json;charset=utf-8", (request, response) -> {

			String message = request.body();
			Usuario usuario = this.gson.fromJson(message, Usuario.class);

			response.type("application/json;charset=utf-8");
			
			/* Busco el usuario en el repositorio para validar */
			Usuario user = RepositorioUsuarios.getInstance().getByCredential(usuario.getUser(),usuario.getPassword());
	
			if(user != null)	
				response.status(200);				
			else 
				response.status(401);					
			
			return message;

		}, this.jsonTransformer);

		Spark.get("/profile/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario loggedUser = RepositorioUsuarios.getInstance().getByUsername(username);
	
			Usuario maru = new Usuario.UsuarioBuilder()
			.nombre(loggedUser.getNombre())
			.credenciales(loggedUser.getUser(),loggedUser.getPassword())
			.sexo(loggedUser.getSexo())
			.peso(loggedUser.getPeso())
			.altura(loggedUser.getAltura())
			.rutina(loggedUser.getRutina())	
			.fechaNacimiento(loggedUser.getFechaNacimiento())
			.build();	
			
			Vegano vegano = new Vegano();
			maru.agregarCondicionPreexistente(vegano);

			response.type("application/json;charset=utf-8");
			
			return maru;
		}, this.jsonTransformer);
		
		Spark.get("/profileIMC/:username", "application/json;charset=utf-8", (request, response) -> {

			String username = request.params(":username");
			Usuario loggedUser = RepositorioUsuarios.getInstance().getByUsername(username);
	
			BigDecimal IMC = loggedUser.getIMC(2);
			response.type("application/json;charset=utf-8");
			
			return IMC;
		}, this.jsonTransformer);
	
	}
	
	
}

