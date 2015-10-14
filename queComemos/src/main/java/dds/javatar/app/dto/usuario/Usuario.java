package dds.javatar.app.dto.usuario;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

//import org.uqbar.commons.model.Entity;
import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.PreferenciaUsuario;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
public class Usuario{
	
	public enum Sexo {
		MASCULINO, FEMENINO
	};

	public enum EstadoSolicitud {
		RECHAZADA, ACEPTADA
	};

	private static final Integer MIN_NAME_LENGTH = 4;

	@Id
	@GeneratedValue
	@Column (name="usuario_id")
	private Long idUsuario;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="sexo")
	private Sexo sexo;
	
	@Column(name="fechaNacimiento")
	private Date fechaNacimiento;
	
	@Column(name="altura")
	private BigDecimal altura;
	
	@Column(name="peso")
	private BigDecimal peso;
	
	@Column(name="rutina")
	private Rutina rutina;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_condicion",
	    joinColumns = @JoinColumn(name = "usuario_id"),
	    inverseJoinColumns = @JoinColumn(name = "condicion_id") )
	private Set<CondicionPreexistente> condicionesPreexistentes;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<PreferenciaUsuario> preferenciasAlimenticias;
	
	@OneToMany(mappedBy="autor")
	private Set<Receta> recetas;
	
	@ManyToMany(mappedBy="miembros")
	private Set<GrupoDeUsuarios> gruposAlQuePertenece;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_favoritas",
	    joinColumns = @JoinColumn(name = "usuario_id"),
	    inverseJoinColumns = @JoinColumn(name = "receta_id") )
	private List<Receta> recetasFavoritas;
		
	private EstadoSolicitud estadoSolicitud;
	private boolean favearTodasLasConsultas;
	
	/** Login attributes **/
	private String username;
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
		this.preferenciasAlimenticias = new ArrayList<PreferenciaUsuario>();
		this.recetas = new HashSet<Receta>();
		this.gruposAlQuePertenece = new HashSet<GrupoDeUsuarios>();
		this.recetasFavoritas = new ArrayList<Receta>();
		
		this.username = usuarioBuilder.user;
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
		return username;
	}

	public void setUser(String user) {
		this.username = user;
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
		Componente componente = new Componente(alimento, new BigDecimal(0));
		PreferenciaUsuario preferencia = new PreferenciaUsuario(this,componente,true);
		this.preferenciasAlimenticias.add(preferencia);
	}

	public void agregarAlimentoQueLeDisgusta(String alimento) {
		Componente componente = new Componente(alimento, new BigDecimal(0));
		PreferenciaUsuario preferencia = new PreferenciaUsuario(this,componente,false);
		this.preferenciasAlimenticias.add(preferencia);
	}
	
	public List<PreferenciaUsuario> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public void setPreferenciasAlimenticias(List<PreferenciaUsuario> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public List<String> getComidasSegunPreferecia(Boolean preferencia){
		List<String> comidas = new ArrayList<String>();
		for(PreferenciaUsuario comida: this.preferenciasAlimenticias){
			if(comida.getGusta()==preferencia){
				comidas.add(comida.getComponente().getDescripcion());
			}
		}
		return comidas;
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
		return chequear(alimento, true);
	}

	public Boolean tieneAlimentoQueLeDisguste(String alimento) {
		return chequear(alimento, false);
	}

	public Boolean chequear(String alimento, Boolean gusta){

		Boolean leGusta = false;
		
		for(PreferenciaUsuario preferencia : this.preferenciasAlimenticias) {
			if(preferencia.getComponente().getDescripcion().equals(alimento) && preferencia.getGusta()==gusta) leGusta = true;
		}
	
		return leGusta;
	}
	
	public Boolean tieneAlgunaPreferencia() {
		return (this.preferenciasAlimenticias.contains(Boolean.TRUE));
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
	
	public void updateFavorita(Receta receta){
		
		try {
			Receta recetaEncontrada = this.getFavoritos().stream().filter(o -> o.getNombre().equals(receta.getNombre())).findFirst().get();
			
			this.getFavoritos().remove(recetaEncontrada);
			this.marcarFavorita(receta);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		
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
