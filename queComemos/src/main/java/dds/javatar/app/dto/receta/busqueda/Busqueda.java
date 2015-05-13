package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class Busqueda extends Sistema{
	
	private Usuario usuario;
	
	private List<Receta> recetasXusuario;
	
	private List<Filtro> filtros;

	private List<PostProcesamiento> postProcesamiento;

	
	
	public void filtrar() throws FilterException {
		
		for(Filtro filtro : filtros) {
			
			filtro.filtrarBusqueda(this);
		}
	}
	
	public List<Receta> postProcesar(){
		recetasXusuario = recetasQueConoceEl(usuario);
		List<Receta> recetasProcesadas = new ArrayList<Receta>();
		for(PostProcesamiento procesamientos: postProcesamiento){
			recetasProcesadas.addAll(procesamientos.procesar(recetasXusuario));
		}
		return recetasProcesadas;
	}
	
	public List<PostProcesamiento> getPostProcesamiento() {
		return postProcesamiento;
	}

	public void setPostProcesamiento(List<PostProcesamiento> postProcesamiento) {
		this.postProcesamiento = postProcesamiento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Receta> getRecetasXusuario() {
		return recetasXusuario;
	}

	public void setRecetasXusuario(List<Receta> recetasXusuario) {
		this.recetasXusuario = recetasXusuario;
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}
	
	
	
}
