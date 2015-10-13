package dds.javatar.app.dto.receta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@DiscriminatorValue("CompuestaPrivada")
public class RecetaPrivadaCompuesta extends RecetaCompuesta implements RecetaPrivada {
	
	/** Constructor **/
		public RecetaPrivadaCompuesta(
				String nombre,
				String autor,
				Integer calorias,
				String dificultad,
				String temporada,
				List<Componente> condimentos,
				List<Componente> ingredientes,
				List<Paso> pasosPreparacion,
				HashSet<Receta> subRecetas)
		{
			this.nombre = nombre;
			this.autor = autor;
			this.calorias = calorias;
			this.dificultad = dificultad;
			this.temporada = temporada;
			this.condimentos = new ArrayList<Componente>();
			this.condimentos.addAll(condimentos);
			this.ingredientes = new  ArrayList<Componente>();
			this.ingredientes.addAll(ingredientes);
			this.pasosPreparacion = new ArrayList<Paso>();
			this.pasosPreparacion.addAll(pasosPreparacion);
			this.subRecetas = new HashSet<Receta>();
			this.subRecetas.addAll(subRecetas);
		}

	public Receta privatizarSiCorresponde(Usuario user) throws UsuarioException, RecetaException {
		return this;
	}
	
}
