package dds.javatar.app.dto.receta.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import queComemos.entrega3.dominio.Dificultad;
import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dds.javatar.app.dto.receta.Receta;

public class RecetaAdapter {

	private static RecetaAdapter instanceReceta;
	private static RepoRecetas instanceRepo;
	
	//TODO faltaria ver si realmente hace falta usar estas constantes
	private static final String DIFICULTAD_FACIL = "F";
	private static final String DIFICULTAD_MEDIA = "M";
	private static final String DIFICULTAD_DIFiCIL = "D";

	
	public static RecetaAdapter getInstanceReceta() {
		if (instanceReceta == null) instanceReceta = new RecetaAdapter();
		return instanceReceta;
	}
	
	public static RepoRecetas getInstanceRepo() {
		if (instanceRepo == null) instanceRepo = new RepoRecetas();
		return instanceRepo;
	}
	
	public List<Receta> consultarReceta(String nombre, String dificultad, List<String> palabrasClaves) {
		
		RepoRecetas repo = RecetaAdapter.getInstanceRepo();
		
		BusquedaRecetas busqueda = crearBusqueda(nombre, dificultad, palabrasClaves);
		String jsonReceta = repo.getRecetas(busqueda);
		
		List<Receta> recetas = new ArrayList<Receta>();
		
		recetas = mapearJsonReceta(jsonReceta);
		
		return recetas;
		
	}
	
	private BusquedaRecetas crearBusqueda(String nombre, String dificultad, List<String> palabrasClaves) {
		
		BusquedaRecetas busqueda = new BusquedaRecetas();
		
		busqueda.setNombre(nombre);
		
		Dificultad dificultadReceta = Dificultad.valueOf(dificultad);
		busqueda.setDificultad(dificultadReceta);
		
		for(String palabra : palabrasClaves) {
			busqueda.agregarPalabraClave(palabra);
		}
		
		return busqueda;
	}
	
	private List<Receta> mapearJsonReceta(String json) {
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Receta>>() {}.getType();
		List<Receta> listaRecetas = gson.fromJson(json, listType);
		return listaRecetas;
		
	}
	
	
}
