package dds.javatar.app.dto;

public class Rutina {

	public enum TipoRutina {
		LEVE("Sedentaria con algo de ejercicio"),
		NADA("Sedentaria con nada de ejercicio"),
		MEDIANO("Sedentaria con ejercicio"),
		INTENSIVO("Activa con ejercicio adicional"),
		FUERTE("Activa sin ejercicio adicional");
	
		private String descripcion;
		
		private TipoRutina(String descripcion){
			this.setDescripcion(descripcion);
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	
	};
	
	private TipoRutina tipo;
	private Integer duracion;
	
	public Rutina(TipoRutina tipo, Integer duracion){
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
	
}
