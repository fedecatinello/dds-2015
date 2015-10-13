package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;

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
			HashMap<String, BigDecimal> ingredientes,
			HashMap<String, BigDecimal> condimentos,
			HashMap<Integer, String> pasosPreparacion) 
	{
		this.nombre = nombre;
		this.autor = autor;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.ingredientes.putAll(ingredientes);
		this.condimentos = new HashMap<String, BigDecimal>();
		this.condimentos.putAll(condimentos);
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.pasosPreparacion.putAll(pasosPreparacion);
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

	//Getters & Setters
	
	public void setCondimentos(HashMap<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		
	}

	public void setIngredientes(HashMap<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
		
	}

	public void setPasosPreparacion(HashMap<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		
	}

}
