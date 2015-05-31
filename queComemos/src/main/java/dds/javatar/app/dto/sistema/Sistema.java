package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class Sistema implements RepositorioRecetas {

	private List<Receta> recetaConocidas;

	protected Sistema() {
		this.recetaConocidas = new ArrayList <Receta>();
	}

	private static Sistema instance;

	public static Sistema getInstance() {
		if (instance == null) {
			instance = new Sistema();
		}
		return instance;
	}

	/**		Getter & Setter		**/
	
	@Override
	public void agregar(Receta receta) {
		this.recetaConocidas.add(receta);
	}

	@Override
	public void quitar(Receta receta) throws BusinessException {
			this.recetaConocidas.remove(receta);
	}

	/**		Metodos		**/
	
	@Override
	public List<Receta> listarTodas() {
		return this.recetaConocidas;
	}

	public void eliminarTodasLasRecetas(){
		this.recetaConocidas.clear();
	}
	
	public void sugerir(Receta receta, Usuario usuario)
			throws BusinessException {
		for (String ingrediente : receta.getIngredientes().keySet()) {
			if (!usuario.validarSiAceptaReceta(receta)
					|| usuario.tieneAlimentoQueLeDisguste(ingrediente)) {
				throw new BusinessException("la receta: " + receta.getNombre()
						+ " no puede ser sugerida al usuario"
						+ usuario.getNombre());
			}
		}
	}

	public void sugerir(Receta receta, GrupoDeUsuarios grupo)
			throws BusinessException {
		for (String preferencia : grupo.getPreferenciasAlimenticias().keySet()) {

			if (!receta.contieneCondimento(preferencia)
					|| !receta.contieneIngrediente(preferencia)
					|| !(receta.getNombre() == preferencia)) {
				throw new BusinessException("La receta:" + receta.getNombre()
						+ " no contiene palabra clave del grupo:"
						+ grupo.getNombre());
			}
			for (Usuario integrante : grupo.getUsuarios()) {
				integrante.validarSiAceptaReceta(receta);
			}
		}
	}

	public List<Receta> recetasQueConoceEl(Usuario usuario) {
		
		List<Receta> recetasQueConoceLista;
		Set<Receta> recetasQueConoceSet = new LinkedHashSet<Receta> (this.recetaConocidas);
		recetasQueConoceSet.addAll(usuario.getRecetas());
		
		if (!usuario.getGruposAlQuePertenece().isEmpty()	|| usuario.getGruposAlQuePertenece() != null) {	
			for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
				for (Usuario miembroDelGrupo : grupo.getUsuarios()) {					
						recetasQueConoceSet.addAll(miembroDelGrupo.getRecetas());
				}
			}
		}
		recetasQueConoceLista = new ArrayList<Receta>(recetasQueConoceSet);
		return recetasQueConoceLista;
	}

	public List<Receta> realizarBusquedaPara(Busqueda busqueda, Usuario usuario)
			throws FilterException {

		List<Receta> recetasXusuario = this.recetasQueConoceEl(usuario);
		busqueda.filtrar(usuario, recetasXusuario);
		busqueda.postProcesar(recetasXusuario);
		return recetasXusuario;

	}

}
