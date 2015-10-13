package dds.javatar.app.dto.receta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@DiscriminatorValue("SimplePrivada")
public class RecetaPrivadaSimple extends RecetaSimple implements RecetaPrivada {

	/**** Constructor ****/
	public RecetaPrivadaSimple(
			String nombre,
			String autor,
			Integer calorias,
			String dificultad,
			String temporada,
			List<Componente> ingredientes,
			List<Componente> condimentos,
			List<Paso> pasosPreparacion) 
	{
		this.nombre = nombre;
		this.autor = autor;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.ingredientes = new ArrayList<Componente>();
		this.ingredientes.addAll(ingredientes);
		this.condimentos = new ArrayList<Componente>();
		this.condimentos.addAll(condimentos);
		this.pasosPreparacion = new ArrayList<Paso>();
		this.pasosPreparacion.addAll(pasosPreparacion);
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}


	@Override
	public Receta privatizarSiCorresponde(Usuario usuario)
			throws UsuarioException, RecetaException {
		return this;
	}


}
