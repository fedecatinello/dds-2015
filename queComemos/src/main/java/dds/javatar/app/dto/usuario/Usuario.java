package dds.javatar.app.dto.usuario;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uqbar.commons.model.Entity;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@SuppressWarnings("serial")
public class Usuario extends Entity {

	public enum Sexo {
		MASCULINO, FEMENINO
	};

	public enum EstadoSolicitud {
		RECHAZADA, ACEPTADA
	};

	private static final Integer MIN_NAME_LENGTH = 4;

	private String nombre;
	private Sexo sexo;
	private Date fechaNacimiento;
	private BigDecimal altura;
	private BigDecimal peso;
	private EstadoSolicitud estadoSolicitud;

	private Set<CondicionPreexistente> condicionesPreexistentes;
	private Map<String, Boolean> preferenciasAlimenticias;
	private Rutina rutina;
	private Set<Receta> recetas;
	private Set<GrupoDeUsuarios> gruposAlQuePertenece;
	private List<Receta> recetasFavoritas;
	private boolean favearTodasLasConsultas;
	
	/** Login attributes **/
	private String user;
	private String password;

	/**** Constructors ****/

	private Usuario(UsuarioBuilder usuarioBuilder) {
		this.nombre = usuarioBuilder.nombre;
		this.sexo = usuarioBuilder.sexo;
		this.fechaNacimiento = usuarioBuilder.fechaNacimiento;
		this.altura = usuarioBuilder.altura;
		this.peso = usuarioBuilder.peso;
		this.estadoSolicitud = usuarioBuilder.estadoSolicitud;

		this.rutina = usuarioBuilder.rutina;
		this.condicionesPreexistentes = new HashSet<CondicionPreexistente>();
		this.preferenciasAlimenticias = new HashMap<String, Boolean>();
		this.recetas = new HashSet<Receta>();
		this.gruposAlQuePertenece = new HashSet<GrupoDeUsuarios>();
		this.recetasFavoritas = new ArrayList<Receta>();
		
		this.user = usuarioBuilder.user;
		this.password = usuarioBuilder.password;
	}

	public static class UsuarioBuilder {
		private String nombre;
		private Sexo sexo;
		private Date fechaNacimiento;
		private BigDecimal altura;
		private BigDecimal peso;
		private EstadoSolicitud estadoSolicitud;
		private Rutina rutina;
		
		private String user;
		private String password;

		public UsuarioBuilder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public UsuarioBuilder sexo(Sexo sexo) {
			this.sexo = sexo;
			return this;
		}

		public UsuarioBuilder fechaNacimiento(Date fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
			return this;
		}

		public UsuarioBuilder altura(BigDecimal altura) {
			this.altura = altura;
			return this;
		}

		public UsuarioBuilder peso(BigDecimal peso) {
			this.peso = peso;
			return this;
		}

		public UsuarioBuilder estadoSolicitud(EstadoSolicitud estadoSolicitud) {
			this.estadoSolicitud = estadoSolicitud;
			return this;
		}

		public UsuarioBuilder rutina(Rutina rutina) {
			this.rutina = rutina;
			return this;
		}
		
		public UsuarioBuilder credenciales(String usuario, String contrasenia) {
			this.user = usuario;
			this.password = contrasenia;
			return this;
		}

		public Usuario build() {
			return new Usuario(this);
		}

	}

	/**** Setters y getters ****/
	public BigDecimal getAltura() {
		return this.altura;
	}

	public BigDecimal getPeso() {
		return this.peso;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public Rutina getRutina() {
		return this.rutina;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Receta> getRecetas() {
		return this.recetas;
	}

	public Set<GrupoDeUsuarios> getGruposAlQuePertenece() {
		return this.gruposAlQuePertenece;
	}

	public Set<CondicionPreexistente> getCondicionesPreexistentes() {
		return this.condicionesPreexistentes;
	}

	public List<Receta> getFavoritos() {
		return this.recetasFavoritas;
	}

	public void agregarGruposAlQuePertenece(GrupoDeUsuarios grupoAlQuePertenece) {
		this.gruposAlQuePertenece.add(grupoAlQuePertenece);
	}

	public void agregarPreferenciaAlimenticia(String alimento) {
		this.preferenciasAlimenticias.put(alimento, Boolean.TRUE);
	}

	public void agregarAlimentoQueLeDisgusta(String alimento) {
		this.preferenciasAlimenticias.put(alimento, Boolean.FALSE);
	}

	public void agregarCondicionPreexistente(CondicionPreexistente condicion) {
		this.condicionesPreexistentes.add(condicion);
	}

	public void agregarReceta(Receta receta) throws RecetaException {
		receta.validarSiLaRecetaEsValida();
		this.getRecetas()
			.add(receta);
	}

	public void quitarReceta(Receta receta) throws UsuarioException {
		if (this.recetas.contains(receta)) {
			this.recetas.remove(receta);
		} else {
			throw new UsuarioException("El Usuario no tenia agregada esa receta");
		}
	}

	public Set<CondicionPreexistente> condicionesQueNoAcepta(Usuario usuario, Receta receta) {
		Set<CondicionPreexistente> condicionesQueNoAceptanReceta = new HashSet<CondicionPreexistente>();
		for (CondicionPreexistente condicionPreexistente : usuario.condicionesPreexistentes) {
			if (!usuario.validarSiAceptaReceta(receta)) {
				condicionesQueNoAceptanReceta.add(condicionPreexistente);
			}
		}
		return condicionesQueNoAceptanReceta;
	}

	public void habilitarOpcionFavearTodas() {
		this.setFavearTodasLasConsultas(true);
	}

	public void desHabilitarOpcionFavearTodas() {
		this.setFavearTodasLasConsultas(false);
	}

	/**** Metodos ****/

	/* Validadores */

	public void validar() throws UsuarioException {
		this.validarCamposNulos();
		this.validarNombre();
		this.validarFechaNacimiento();
		this.validarCondicionesPreexistentes();
	}

	private void validarCamposNulos() throws UsuarioException {
		if (this.nombre == null || this.fechaNacimiento == null || this.peso == null || this.altura == null || this.rutina == null) {
			throw new UsuarioException("El usuario tiene campos obligatorios sin completar");
		}
	}

	private void validarNombre() throws UsuarioException {
		if (this.nombre.length() <= MIN_NAME_LENGTH) {
			throw new UsuarioException("El nombre del usuario es demasido corto");
		}
	}

	private void validarFechaNacimiento() throws UsuarioException {
		Date today = new Date();
		if (today.compareTo(this.fechaNacimiento) <= 0) {
			throw new UsuarioException("La fecha de nacimiento del usuario no puede posterior al dia de hoy.");
		}
	}

	private void validarCondicionesPreexistentes() throws UsuarioException {
		for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
			condicionPreexistente.validarUsuario(this);
		}
	}

	public boolean validarSiAceptaReceta(Receta receta) {
		for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
			if (!condicionPreexistente.validarReceta(receta)) {
				return false;
			}
		}
		return true;
	}

	public void validarModificarReceta(Receta receta) throws UsuarioException {
		receta.chequearVisibilidad(receta, this);
	}

	/* Obtener la masa corporal dada una presicion */
	public BigDecimal getIMC(int precision) {

		MathContext mc = new MathContext(precision, RoundingMode.HALF_DOWN);
		BigDecimal cuadrado = this.altura.pow(2, mc);
		return this.peso.divide(cuadrado, mc);
	}

	public void puedeAgregarSubRecetas(Set<Receta> subRecetas) throws UsuarioException {
		for (Receta subReceta : subRecetas) {
			try {
				this.puedeVerReceta(subReceta);
			} catch (Exception e) {
				throw new UsuarioException("El Usuario no tiene permitido agregar alguna subreceta");
			}
		}
	}

	public Boolean sigueRutinaSaludable() {

		int userIMC = this.getIMC(MathContext.DECIMAL32.getPrecision())
			.intValue();

		if (userIMC < 18 || userIMC > 30) {
			return Boolean.FALSE;
		}

		for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
			if (!condicionPreexistente.usuarioSigueRutinaSaludable(this)) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;
	}

	public Boolean tienePreferenciaAlimenticia(String alimento) {
		return Boolean.TRUE.equals(this.preferenciasAlimenticias.get(alimento));
	}

	public Boolean tieneAlimentoQueLeDisguste(String alimento) {
		return Boolean.FALSE.equals(this.preferenciasAlimenticias.get(alimento));
	}

	public Boolean tieneAlgunaPreferencia() {
		return (this.preferenciasAlimenticias.values().contains(Boolean.TRUE));
	}

	public void puedeVerReceta(Receta receta) throws UsuarioException {
		if (!receta.chequearVisibilidad(receta, this)) {
			throw new UsuarioException("El Usuario no tiene permitido ver esta receta");
		}
	}

	public Receta puedeModificarReceta(Receta receta) throws UsuarioException, RecetaException {
		if (!receta.chequearVisibilidad(receta, this)) {
			throw new UsuarioException("El Usuario no tiene permitido modificar esta receta");
		} else {
			return receta.privatizarSiCorresponde(this);
		}
	}

	public void modificarNombreDeReceta(Receta receta, String nuevoNombre) throws UsuarioException, RecetaException {
		receta = this.puedeModificarReceta(receta);
		receta.setNombre(nuevoNombre);
	}

	// Entrega 3: Punto 2

	public List<Receta> consultarRecetasExternas(Busqueda busqueda) {

		Buscador buscador = new Buscador();
		List<Receta> recetas = buscador.buscarRecetasExternas(this, busqueda);
		return recetas;

	}

	public Boolean esVegano() {
		for (CondicionPreexistente condicionPreexistente : this.condicionesPreexistentes) {
			if (condicionPreexistente.esVegano()) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	public EstadoSolicitud getEstadoSolicitud() {
		return this.estadoSolicitud;
	}

	public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public boolean isFavearTodasLasConsultas() {
		return this.favearTodasLasConsultas;
	}

	public void setFavearTodasLasConsultas(boolean favearTodasLasConsultas) {
		this.favearTodasLasConsultas = favearTodasLasConsultas;
	}

	// Entrega 4 - PUnto 5

	public void marcarFavorita(Receta receta) {
		this.recetasFavoritas.add(receta);
	}

	public boolean tieneReceta(Receta receta) {
		for (Receta recetaUser : this.getRecetas()) {
			if (recetaUser.getNombre()
				.equals(receta.getNombre()))
				return true;
		}
		return false;
	}
	
	public boolean tieneRecetaFavorita(Receta receta) {
		for (Receta recetaUser : this.getFavoritos()) {
			if (recetaUser.getNombre()
				.equals(receta.getNombre()))
				return true;
		}
		return false;
	}

}
