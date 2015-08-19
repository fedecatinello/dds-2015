package dds.javatar.app.ui.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.PostProcesamiento;
import dds.javatar.app.dto.receta.busqueda.PrimerosDiez;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.receta.RecetaContainer;


@Observable
public class Home {
	private Usuario usuarioLogeado;  // TODO  
	private String mensajeInicio;
	private List<Receta> recetasFav;
	private List<Receta> recetasCons;
	private List<Receta> recetasTop;
	private Receta recetaSelect;
	private Boolean enableButton;
	
	private static Buscador buscador;

    public Buscador getInstanceBuscador() {
   	 if (buscador == null) {
   		 buscador = new Buscador();
   	 }
   	 return buscador;
    }

    
    public List<Receta> buscarTops(){
    	PostProcesamiento primerosDiez = new PrimerosDiez();
    	Buscador buscadorTop = getInstanceBuscador();
    	buscadorTop.setPostProcesamiento(primerosDiez);
    	Busqueda busqueda = new Busqueda.BusquedaBuilder().build();

    	List<Receta> recetasXusuario = buscadorTop.recetasQueConoceEl(this.usuarioLogeado);
    	List<Receta> recetasRepoExterno = buscadorTop.buscarRecetasExternas(this.usuarioLogeado,busqueda);
    	recetasXusuario.addAll(recetasRepoExterno);
    	buscadorTop.postProcesar(recetasXusuario);
    	
    	return recetasXusuario;

    }
    
    public void searchRecetas() {
    	this.recetasTop = this.buscarTops();
    	if(this.recetasCons==null){
    		this.recetasCons = new ArrayList<Receta>();
    	} else {
    		this.recetasCons = RepositorioRecetas.getInstance().listarTodas();
    	}
    	this.recetasFav = this.usuarioLogeado.getFavoritos();		
    }

    public void loadUser() {
    	Usuario usuario = new Usuario.UsuarioBuilder()
		.nombre("ElSiscador")
			.sexo(Usuario.Sexo.MASCULINO)
			.peso(new BigDecimal(75))
			.altura(new BigDecimal(1.80))
			.rutina(new Rutina(TipoRutina.FUERTE, 20))
			.build();
    	RepositorioUsuarios.getInstance().add(usuario);
    	this.usuarioLogeado = usuario;
    }
    
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
		this.setEnableButton(Boolean.FALSE);
	}


	public List<Receta> getRecetasTop() {
		return recetasTop;
	}


	public void setRecetasTop(List<Receta> recetasTop) {
		this.recetasTop = recetasTop;
	}


	public static Buscador getBuscador() {
		return buscador;
	}


	public static void setBuscador(Buscador buscador) {
		Home.buscador = buscador;
	}


	public Boolean getEnableButton() {
		return enableButton;
	}


	public void setEnableButton(Boolean enableButton) {
		this.enableButton = enableButton;
	}
	

}
