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
		return this.receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public Usuario getUsuarioLogeado() {
		return this.usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public String getDuenioDeReceta() {
		String duenioDeReceta;
		if (this.usuarioLogeado.tieneReceta(this.receta)) {
			duenioDeReceta = "creada por vos";
		} else {
			duenioDeReceta = "receta publica";
		}

		return duenioDeReceta;
	}

	public String getPasosPreparacion() {
		Map<Integer, String> pasos = new TreeMap<Integer, String>(this.receta.getPasosPreparacion());
		return String.join(". ", pasos.values());
	}

	public boolean getEsFavorita() {
		return this.usuarioLogeado.tieneRecetaFavorita(this.receta);
	}

	public List<String> getCondiciones() {
		List<String> condiciones = new ArrayList<String>();

		for (CondicionPreexistente condicionPreexistente : RepositorioCondiciones.getInstance().condicionesPreexistentes) {
			if (!condicionPreexistente.validarReceta(this.receta)) {
				condiciones.add(condicionPreexistente.getName());
			}
		}
		return condiciones;
	}

	public String getCaloriasString() {
		return this.receta.getCalorias() + " calorias";
	}

}
