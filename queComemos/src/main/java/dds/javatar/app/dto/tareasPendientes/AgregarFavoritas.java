package dds.javatar.app.dto.tareasPendientes;


import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class AgregarFavoritas implements TareaPendiente {

	private Usuario usuario;
	private List<Receta> recetas;

	public AgregarFavoritas(Usuario usuario, List<Receta> recetas) {
		this.usuario = usuario;
		this.recetas = recetas;
	}
	
	@Override
	public void execute() {
		if(this.usuario.isFavearTodasLasConsultas()){
			for(Receta receta: this.recetas){
				if(!usuario.getFavoritos().contains(receta)){
					usuario.getFavoritos().add(receta);
				}
			}
		}
	}

}
