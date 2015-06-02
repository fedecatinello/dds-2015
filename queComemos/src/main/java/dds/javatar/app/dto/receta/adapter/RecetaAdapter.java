package dds.javatar.app.dto.receta.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import queComemos.entrega3.dominio.Dificultad;
import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.monitoreo.ConsultaObserver;

public class RecetaAdapter {

	private static RecetaAdapter instanceReceta;
	private static RepoRecetas instanceRepo;

	// TODO faltaria ver si realmente hace falta usar estas constantes
	// pg: para mi hay que hacer un mapita porque el de ellos no son letras sino palabras, no?

	private Map<String, Dificultad> dificultades;
	private Set<ConsultaObserver> observers;

	private RecetaAdapter() {
		this.observers = new HashSet<ConsultaObserver>();
		this.dificultades = new HashMap<String, Dificultad>();
		this.dificultades.put("F", Dificultad.FACIL);
		this.dificultades.put("M", Dificultad.MEDIANA);
		this.dificultades.put("D", Dificultad.DIFICIL);
	}

	public static RecetaAdapter getInstanceReceta() {
		if (instanceReceta == null)
			instanceReceta = new RecetaAdapter();
		return instanceReceta;
	}

	public static RepoRecetas getInstanceRepo() {
		if (instanceRepo == null)
			instanceRepo = new RepoRecetas();
		return instanceRepo;
	}

	public List<Receta> consultarReceta(Usuario usuario, String nombre, String dificultadStr, List<String> palabrasClaves) {

		Dificultad dificultad = this.dificultades.get(dificultadStr);

		this.observers.forEach(observer -> observer.notificarConsulta(usuario, nombre, dificultad));

		RepoRecetas repo = RecetaAdapter.getInstanceRepo();

		BusquedaRecetas busqueda = this.crearBusqueda(nombre, dificultad, palabrasClaves);
		String jsonReceta = repo.getRecetas(busqueda);

		List<Receta> recetas = new ArrayList<Receta>();

		recetas = this.mapearJsonReceta(jsonReceta);

		return recetas;

	}

	private BusquedaRecetas crearBusqueda(String nombre, Dificultad dificultad, List<String> palabrasClaves) {

		BusquedaRecetas busqueda = new BusquedaRecetas();

		busqueda.setNombre(nombre);

		busqueda.setDificultad(dificultad);

		for (String palabra : palabrasClaves) {
			busqueda.agregarPalabraClave(palabra);
		}

		return busqueda;
	}

	public void addObserver(ConsultaObserver observer) {
		this.observers.add(observer);
	}

	private List<Receta> mapearJsonReceta(String json) {

		Gson gson = new Gson();
		Type listType = new TypeToken<List<Receta>>() {
		}.getType();
		List<Receta> listaRecetas = gson.fromJson(json, listType);
		return listaRecetas;

	}

}
