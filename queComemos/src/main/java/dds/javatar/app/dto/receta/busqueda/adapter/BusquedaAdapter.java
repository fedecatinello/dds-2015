package dds.javatar.app.dto.receta.busqueda.adapter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
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
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.receta.RecetaSimple;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.monitoreo.ConsultaObserver;

public class BusquedaAdapter {

	private static RepoRecetas instanceRepo;
	private static BusquedaAdapter instanceAdapter;

	private Map<String, Dificultad> dificultades;
	private Set<ConsultaObserver> observers;

	public BusquedaAdapter() {
		this.observers = new HashSet<ConsultaObserver>();
		this.dificultades = new HashMap<String, Dificultad>();
		this.dificultades.put("F", Dificultad.FACIL);
		this.dificultades.put("M", Dificultad.MEDIANA);
		this.dificultades.put("D", Dificultad.DIFICIL);
	}

	public static RepoRecetas getInstanceRepo() {
		if (instanceRepo == null)
			instanceRepo = new RepoRecetas();
		return instanceRepo;
	}

	public static BusquedaAdapter getInstance() {
		if (instanceAdapter == null)
			instanceAdapter = new BusquedaAdapter();
		return instanceAdapter;
	}

	public List<Receta> consultarRecetas(Usuario usuario, String nombre, String dificultadStr, List<String> palabrasClaves) {

		RepoRecetas repo = BusquedaAdapter.getInstanceRepo();

		Dificultad dificultad = this.dificultades.get(dificultadStr);

		this.observers.forEach(observer -> observer.notificarConsulta(usuario, nombre, dificultad));

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

	private List<Receta> mapearJsonReceta(String json) {

		Gson gson = new Gson();
		Type listType = new TypeToken<List<queComemos.entrega3.dominio.Receta>>() {
		}.getType();

		List<queComemos.entrega3.dominio.Receta> listaRecetas = gson.fromJson(json, listType);

		List<Receta> recetasUsuario = new ArrayList<Receta>();

		listaRecetas.forEach(receta -> this.agregarReceta(receta, recetasUsuario));

		return recetasUsuario;

	}

	public void agregarReceta(queComemos.entrega3.dominio.Receta receta, List<Receta> recetasUsuario) {

		RecetaSimple recetaUsuario = new RecetaPublicaSimple();
		recetaUsuario.setNombre(receta.getNombre());
		receta.getIngredientes().forEach(ingrediente -> recetaUsuario.agregarIngrediente(ingrediente, new BigDecimal(0)));
		recetaUsuario.setTiempoPreparacion(receta.getTiempoPreparacion());
		recetaUsuario.setCalorias(receta.getTotalCalorias());
		recetaUsuario.setDificultad(receta.getDificultadReceta().toString());
		recetaUsuario.setAutor(receta.getAutor());
		recetaUsuario.setAnioCreacion(receta.getAnioReceta());

		recetasUsuario.add(recetaUsuario);
	}

	public void addObserver(ConsultaObserver observer) {
		this.observers.add(observer);
	}

}
