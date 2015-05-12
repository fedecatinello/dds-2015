package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Usuario;

public class Busqueda extends Sistema{
	
	private Usuario usuario;

	private List<PostProcesamiento> postProcesamiento;

	public List<Receta> postProcesar(){
		List<Receta> recetasXusuario = recetasQueConoceEl(usuario);
		List<Receta> recetasProcesadas=null;
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
	
	
	
}
