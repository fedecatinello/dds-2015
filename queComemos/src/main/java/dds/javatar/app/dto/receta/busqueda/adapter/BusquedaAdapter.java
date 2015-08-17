package dds.javatar.app.dto.receta.busqueda.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import queComemos.entrega3.dominio.Dificultad;
import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
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

	public List<Receta> consultarRecetas(Usuario usuario, Busqueda busqueda) {

		RepoRecetas repo = BusquedaAdapter.getInstanceRepo();

		this.observers.forEach(observer -> observer.notificarConsulta(usuario, busqueda));

		BusquedaRecetas busquedaRecetasRepo = this.crearBusquedaRepo(busqueda);

		String jsonReceta = repo.getRecetas(busquedaRecetasRepo);

		List<Receta> recetas = new ArrayList<Receta>();

		recetas = this.mapearJsonReceta(jsonReceta);

		return recetas;

	}

	private BusquedaRecetas crearBusquedaRepo(Busqueda busqueda) {

		BusquedaRecetas busquedaRepo = new BusquedaRecetas();

		busquedaRepo.setNombre(busqueda.nombre());
		busquedaRepo.setDificultad(busqueda.dificultad());

		for (String palabra : busqueda.palabrasClave()) {
			busquedaRepo.agregarPalabraClave(palabra);
		}

		return busquedaRepo;
	}

	private List<Receta> mapearJsonReceta(String json) {

		Gson gson = new Gson();

		// Creo json parser
		JsonParser parser = new JsonParser();
		// Obtengo json array
		JsonArray array = parser.parse(json).getAsJsonArray();

		// Creo lista de recetas del componente y un iterador para obtenerlas
		// del json
		List<queComemos.entrega3.dominio.Receta> listaRecetas = new ArrayList<queComemos.entrega3.dominio.Receta>();

		Iterator<JsonElement> iterator = array.iterator();

		iterator.forEachRemaining(element -> obtenerReceta(element, gson, listaRecetas));

		// Creo listas de recetas del usuario y mapeo recetas para agregarlas a
		// esta lista
		List<Receta> recetasUsuario = new ArrayList<Receta>();

		listaRecetas.forEach(receta -> this.agregarReceta(receta, recetasUsuario));

		return recetasUsuario;

	}

	public void agregarReceta(queComemos.entrega3.dominio.Receta receta, List<Receta> recetasUsuario) {

		/** Me guardo los ingredientes **/
		HashMap<String, BigDecimal> ingredientesReceta = new HashMap<String, BigDecimal>();
		receta.getIngredientes().forEach(ingrediente -> ingredientesReceta.put(ingrediente, new BigDecimal(0)));

		
		Receta recetaUsuario = new RecetaBuilder(receta.getNombre())
							.tiempoPreparacion(receta.getTiempoPreparacion())
							.totalCalorias(receta.getTotalCalorias())
							.dificultad(receta.getDificultadReceta().toString())
							.inventadaPor(receta.getAutor())
							.inventadaEn(receta.getAnioReceta())
							.agregarIngredientes(ingredientesReceta)
							.buildReceta();
		
		
		recetasUsuario.add(recetaUsuario);
	}

	public void obtenerReceta(JsonElement element, Gson gson, List<queComemos.entrega3.dominio.Receta> listaRecetas) {

		queComemos.entrega3.dominio.Receta receta = gson.fromJson(element, queComemos.entrega3.dominio.Receta.class);

		listaRecetas.add(receta);
	}

	public void addObserver(ConsultaObserver observer) {
		this.observers.add(observer);
	}

}
