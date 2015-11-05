package dds.javatar.app.dto.sistema;

import java.util.HashSet;

import dds.javatar.app.dto.usuario.condiciones.Celiaco;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;

public class RepositorioIngredientes {

	public HashSet<CondicionPreexistente> ingredientes;
	
	public RepositorioIngredientes() {
		this.ingredientes = crearIngredientes();
	}

	private HashSet<CondicionPreexistente> crearIngredientes() {
		HashSet<CondicionPreexistente> condicionesAll = new HashSet<CondicionPreexistente>();
		Vegano vegano = new Vegano();
		Hipertenso hipertenso = new Hipertenso();
		Diabetico diabetico = new Diabetico();
		Celiaco celiaco = new Celiaco();
		
//
		condicionesAll.add(vegano);
		condicionesAll.add(hipertenso);
		condicionesAll.add(diabetico);
		condicionesAll.add(celiaco);
		return condicionesAll;
	}

	private static RepositorioCondiciones instance;

	public static RepositorioCondiciones getInstance() {
		if (instance == null) {
			instance = new RepositorioCondiciones();
		}
		return instance;
	}

}