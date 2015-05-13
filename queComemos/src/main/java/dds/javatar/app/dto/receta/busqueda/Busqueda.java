package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class Busqueda{
	
	private List<Filtro> filtros;

	private PostProcesamiento postProcesamiento;

	
	public void filtrar(List<Receta> recetas, Usuario usuario) throws FilterException {
		
		for(Filtro filtro : filtros) {
			
			filtro.filtrarBusqueda(recetas,usuario);
		}
	}
	
	public void postProcesar(List<Receta> recetasXusuario){
		postProcesamiento.procesar(recetasXusuario);

	}
	
	public List<Receta> buscarPara(Usuario usuario){
		
		List<Receta> recetasXusuario = Sistema.getInstance().recetasQueConoceEl(usuario);
		filtrar(recetasXusuario,usuario);
		postProcesar(recetasXusuario);
		return recetasXusuario;
	
	}
	
	
	public PostProcesamiento getPostProcesamiento() {
		return postProcesamiento;
	}

	public void setPostProcesamiento(PostProcesamiento postProcesamiento) {
		this.postProcesamiento = postProcesamiento;
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}
	
	
	
}
