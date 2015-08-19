package dds.javatar.app.ui.receta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.uqbar.commons.utils.Observable;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.RepositorioCondiciones;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;


@Observable
public class RecetaModel {

	private Receta receta;
	private Usuario usuarioLogeado; // TODO

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public String getDuenioDeReceta() {
		String duenioDeReceta;
		if (this.usuarioLogeado.tieneReceta(receta)) {
			duenioDeReceta = "creada por vos";
		} else {
			duenioDeReceta = "receta publica";
		}

		return duenioDeReceta;
	}

	public String getPasosPreparacion() {
		String pasosPreparacion;
		Map<Integer, String> pasos = new TreeMap<Integer, String>(
				receta.getPasosPreparacion());

		pasosPreparacion = String.join(". ", pasos.values());
		return pasosPreparacion;
	}

	public boolean getEsFavorita() {
		boolean esFavorita = this.usuarioLogeado.tieneRecetaFavorita(receta);
		return esFavorita;
	}
	
	public List<String> getCondiciones(){
			
		List<String> condiciones = new ArrayList<String>();
		
		for (CondicionPreexistente condicionPreexistente : RepositorioCondiciones.getInstance().condicionesPreexistentes) {
			if (!condicionPreexistente.validarReceta(receta)) {
				condiciones.add(condicionPreexistente.getName());
			}
		}
		return condiciones;
	}

}
