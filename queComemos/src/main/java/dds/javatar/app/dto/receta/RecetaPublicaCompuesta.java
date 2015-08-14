package dds.javatar.app.dto.receta;

import java.util.HashSet;

import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class RecetaPublicaCompuesta extends RecetaCompuesta implements RecetaPublica {

	/** Builder **/
	public RecetaPublicaCompuesta() {
		this.subRecetas = new HashSet<Receta>();
		this.agregarRecetaAlRepo(this);
	}
	
	@Override
	public void agregarRecetaAlRepo(RecetaPublica receta) {
		RepositorioRecetas.getInstance().agregar(receta);
	}

	public Receta privatizarSiCorresponde (Usuario user) throws UsuarioException, RecetaException{
		RecetaCompuesta recetaClonada = new RecetaPrivadaCompuesta();
		recetaClonada.nombre = this.nombre;
		recetaClonada.dificultad = this.dificultad;
		recetaClonada.temporada = this.temporada;
			
		for (Receta receta : this.subRecetas) {
			recetaClonada.agregarSubReceta((RecetaPrivada) receta.privatizarSiCorresponde(user));
		}
		
		user.agregarReceta(recetaClonada);
		user.quitarReceta(this);
		return recetaClonada;
		
	}

		
}
