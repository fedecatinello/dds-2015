package dds.javatar.app.dto.tareasPendientes;


import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;

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
				if(this.usuario.tieneReceta(receta) && !this.usuario.tieneRecetaFavorita(receta)){				
					this.usuario.marcarFavorita(receta);
				}
				else if (!this.usuario.tieneReceta(receta) && !this.usuario.tieneRecetaFavorita(receta))
				{
					try {
						this.usuario.agregarReceta(receta);
						this.usuario.marcarFavorita(receta);
					} catch (RecetaException e) {}
				}
			}
		}
	}

}
