package dds.javatar.app.dto.usuario;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Usuario {

	public enum Sexo {
		MASCULINO, FEMENINO
	};

	private static final Integer MIN_NAME_LENGTH = 4;

	private String nombre;
	private Sexo sexo;
	private Date fechaNacimiento;
	private BigDecimal altura;
	private BigDecimal peso;

	private Set<CondicionPreexistente> condicionesPreexistentes;
	private Map<String, Boolean> preferenciasAlimenticias;
	private Rutina rutina;
	private Set<Receta> recetas;

	/* Constructors */

	public Usuario() {
		this.condicionesPreexistentes = new HashSet<CondicionPreexistente>();
		this.preferenciasAlimenticias = new HashMap<String, Boolean>();
		this.recetas = new HashSet<Receta>();
	}

	public Usuario(BigDecimal altura, BigDecimal peso) {
		this();
		this.altura = altura;
		this.peso = peso;
	}

	public Usuario(BigDecimal altura, BigDecimal peso, Sexo sexo) {
		this(altura, peso);
		this.sexo = sexo;
	}

	/* Setters y getters */

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Map<String, Boolean> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public void setPreferenciasAlimenticias(Map<String, Boolean> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	/* Obtener la masa corporal dada una presicion */

	public BigDecimal getIMC(int precision) {

		MathContext mc = new MathContext(precision, RoundingMode.HALF_DOWN);
		BigDecimal cuadrado = this.altura.pow(2, mc);
		return this.peso.divide(cuadrado, mc);
	}

	// TODO: deberiamos crear una especie de validadores ? porque queda medio
	// feo chequear todos los campos asi, nose..
	public void validar() throws BusinessException {
		if (nombre == null || fechaNacimiento == null || peso == null || altura == null || rutina == null) {
			throw new BusinessException("El usuario tiene campos obligatorios sin completar");
		}

		if (nombre.length() <= MIN_NAME_LENGTH) {
			throw new BusinessException("El nombre del usuario es demasido corto");
		}

		Date today = new Date();
		if (today.compareTo(this.fechaNacimiento) <= 0) {
			throw new BusinessException("La fecha de nacimiento del usuario no puede posterior al dia de hoy.");
		}

		if (this.condicionesPreexistentes != null) {
			for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
				condicionPreexistente.validarUsuario(this);
			}
		}
	}

	public void validarRutinaSaludable() throws BusinessException {

		int userIMC = this.getIMC(MathContext.DECIMAL32.getPrecision()).intValue();

		if (userIMC < 18 || userIMC > 30) {
		    throw new BusinessException("El IMC debe estar en el rango entre 18 y 30");
		}

		if (this.condicionesPreexistentes != null) {
			for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
				condicionPreexistente.validarUsuarioSaludable(this);
			}
		}
	}

	public Boolean tienePreferenciaAlimenticia(String  alimento){ 
		return Boolean.TRUE.equals(this.preferenciasAlimenticias.get("fruta"));
	}
	
	public void agregarReceta(Receta receta) throws BusinessException {
		receta.validar();
		this.recetas.add(receta);
		receta.setAutor(this);
	}
	
	public void agregarCondicionPreexistente(CondicionPreexistente condicion) {
		this.condicionesPreexistentes.add(condicion);
	}

	public void validarSiAceptaReceta(Receta receta) throws BusinessException {
		if (this.condicionesPreexistentes != null) {
			for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
				condicionPreexistente.validarReceta(receta);
			}
		}
	}
	
	
	public void verReceta(Receta receta) throws BusinessException {
		if (!recetas.contains(receta) && receta.getAutor()!=null){
			  throw new BusinessException("El Usuario no tiene permitido ver esta receta");
		}
	}

	public void puedeModificarReceta(Receta receta) throws BusinessException {
		this.verReceta(receta);
	}
	
/*	
	public void modificarReceta(Receta receta) throws BusinessException	{
		if(!this.recetas.contains(receta)){
		receta.setAutor(this);
		this.agregarReceta(receta);
		}
		receta.modificar();
	}
*/
	
}
