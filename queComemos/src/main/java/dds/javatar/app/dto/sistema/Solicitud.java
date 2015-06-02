package dds.javatar.app.dto.sistema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.CondicionPreexistente;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.Sexo;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class Solicitud {
	public enum Sexo {
		MASCULINO, FEMENINO
	};
	
	public enum solicitud {
		RECHAZADA, ACEPTADA
	};

	private static final Integer MIN_NAME_LENGTH = 4;

	private  String nombre;
	private  Sexo sexo;
	private  Date fechaNacimiento;
	private  BigDecimal altura;
	private  BigDecimal peso;

	private  Set<CondicionPreexistente> condicionesPreexistentes;
	private  Map<String, Boolean> preferenciasAlimenticias;
	private  Rutina rutina;
	private  Set<Receta> recetas;
	private  Set<GrupoDeUsuarios> gruposAlQuePertenece;
	private  List<Receta> recetasFavoritas;

	/**** Constructors ****/

	public Solicitud() {
		this.condicionesPreexistentes = new HashSet<CondicionPreexistente>();
		this.preferenciasAlimenticias = new HashMap<String, Boolean>();
		this.recetas = new HashSet<Receta>();
		this.gruposAlQuePertenece = new HashSet<GrupoDeUsuarios>();
		this.recetasFavoritas = new ArrayList<Receta>();
	}

	public Solicitud(BigDecimal altura, BigDecimal peso) {
		this();
		this.altura = altura;
		this.peso = peso;
	}

	public Solicitud(BigDecimal altura, BigDecimal peso, Sexo sexo) {
		this(altura, peso);
		this.sexo = sexo;
	}

	/**** Setters y getters ****/
	

	public Solicitud altura(BigDecimal altura) {
		this.altura = altura;
		return this;
	}


	public Solicitud setPeso(BigDecimal peso) {
		this.peso = peso;
		return this;
	}

	public Solicitud nombre(String nombre) {
		 this.nombre =nombre;
		return this;
	}

	public Solicitud sexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}

	public Solicitud fechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		return this;
	}

	public Solicitud rutina(Rutina rutina) {
		this.rutina = rutina;
		return this;
	}

	public Solicitud recetas(Set<Receta> recetas) {
		this.recetas = recetas;
		return this;
	}

	public Solicitud gruposAlQuePertenece(GrupoDeUsuarios grupoAlQuePertenece) {
		this.gruposAlQuePertenece.add(grupoAlQuePertenece);
		return this;
	}

	public Solicitud agregarPreferenciaAlimenticia(String alimento) {
		this.preferenciasAlimenticias.put(alimento, Boolean.TRUE);
		return this;
	}

	public Solicitud agregarAlimentoQueLeDisgusta(String alimento) {
		this.preferenciasAlimenticias.put(alimento, Boolean.FALSE);
		return this;
	}

	public Solicitud agregarCondicionPreexistente(CondicionPreexistente condicion) {
		this.condicionesPreexistentes.add(condicion);
		return this;
	}
//	
//	 public Usuario build() {
//         return new Usuario(this);
//     }
}
