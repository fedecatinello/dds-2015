package dds.javatar.app.ui.controller;

import java.math.BigDecimal;
import java.util.List;


import spark.Spark;


import com.google.gson.Gson;


import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.ui.controller.util.ContainerFactory;
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

		Spark.get("/recetas", (request, response) -> {		
	
				Buscador buscador = new Buscador();

				Usuario usuarioLogueado;
				usuarioLogueado=new Usuario.UsuarioBuilder().nombre("ElSiscador").build();//RepositorioUsuarios.getInstance().get(new Usuario.UsuarioBuilder().nombre("ElSiscador").build());
				ContainerFactory.getInstance().agregarRecetasAlRepositorio();
				ContainerFactory.getInstance().agregarUsuariosAlRepo();

				List<Receta> recetas = buscador.realizarBusquedaPara(usuarioLogueado);
				
				
				response.type("application/json;charset=utf-8");
			
				return recetas;

			}, this.jsonTransformer);
		
		
	}
}
