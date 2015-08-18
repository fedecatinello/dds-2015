package queComemosUI;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

@Observable
public class Home {
	private Usuario usuarioLogeado;  // TODO  
	private String mensajeInicio;
	private List<Receta> recetasFav;
	private List<Receta> recetasCons;
	private Receta recetaSelect;

	protected String recetasFillType(){
		if(tieneFavoritas()){
			this.setMensajeInicio("Estas son tus recetas favoritas");
			return "favoritas";
		}
		if(hayConsultas()){
			this.setMensajeInicio("Estas son tus últimas consultas");
			return "consultas";
		}
		this.setMensajeInicio("Estas son las 10 recetas más buscadas");
		return "top";
	}

	Boolean tieneFavoritas(){
		return !recetasFav.isEmpty();
	} 
	Boolean hayConsultas(){
		return !recetasCons.isEmpty();
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public String getMensajeInicio() {
		return mensajeInicio;
	}

	public void setMensajeInicio(String mensajeInicio) {
		this.mensajeInicio = mensajeInicio;
	}

	public List<Receta> getRecetasFav() {
		return recetasFav;
	}

	public void setRecetasFav(List<Receta> recetasFav) {
		this.recetasFav = recetasFav;
	}

	public List<Receta> getRecetasCons() {
		return recetasCons;
	}

	public void setRecetasCons(List<Receta> recetasCons) {
		this.recetasCons = recetasCons;
	}

	public Receta getRecetaSelect() {
		return recetaSelect;
	}

	public void setRecetaSelect(Receta recetaSelect) {
		this.recetaSelect = recetaSelect;
	}

}
