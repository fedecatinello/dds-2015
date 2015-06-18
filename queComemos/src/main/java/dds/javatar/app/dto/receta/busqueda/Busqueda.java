package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

public class Busqueda {
	private final String nombre;
	private final String dificultad;
	private final List<String> palabrasClave;

	private Busqueda(BusquedaBuilder builder) {
		this.nombre = builder.nombre;
		this.dificultad = builder.dificultad;
		this.palabrasClave = builder.palabrasClave;
	}

	public String nombre() {
		return nombre;
	}

	public String dificultad() {
		return dificultad;
	}

	public List<String> palabrasClave() {
		return palabrasClave;
	}

	public static class BusquedaBuilder {
		private String nombre;
		private String dificultad;
		private List<String> palabrasClave;

		public BusquedaBuilder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public BusquedaBuilder dificultad(String dificultad) {
			this.dificultad = dificultad;
			return this;
		}

		public BusquedaBuilder palabrasClave(List<String> palabrasClave) {
			this.palabrasClave = palabrasClave;
			return this;
		}

		public Busqueda build() {
			return new Busqueda(this);
		}

	}

}
