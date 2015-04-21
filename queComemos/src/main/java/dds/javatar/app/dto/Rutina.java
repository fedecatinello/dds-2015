package dds.javatar.app.dto;

public class Rutina {

	public enum TipoRutina {
		LEVE(Boolean.FALSE, "Sedentaria con algo de ejercicio"), NADA(Boolean.FALSE, "Sedentaria con nada de ejercicio"), MEDIANO(Boolean.FALSE,
				"Sedentaria con ejercicio"), INTENSIVO(Boolean.TRUE, "Activa con ejercicio adicional"), FUERTE(Boolean.TRUE, "Activa sin ejercicio adicional");

		private String descripcion;
		private Boolean activa;

		private TipoRutina(Boolean activa, String descripcion) {
			this.activa = activa;
			this.descripcion = descripcion;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Boolean getActiva() {
			return activa;
		}

		public void setActiva(Boolean activa) {
			this.activa = activa;
		}

	};

	private TipoRutina tipo;
	private Integer duracion;

	public Rutina(TipoRutina tipo, Integer duracion) {
		this.setTipo(tipo);
		this.setDuracion(duracion);
	}

	public TipoRutina getTipo() {
		return tipo;
	}

	public void setTipo(TipoRutina tipo) {
		this.tipo = tipo;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Boolean esActiva() {
		return this.tipo.getActiva();
	}

	public Boolean esIntensiva() {
		return this.tipo.equals(TipoRutina.INTENSIVO);
	}
}
