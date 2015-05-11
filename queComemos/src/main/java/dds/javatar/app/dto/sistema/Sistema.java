package dds.javatar.app.dto.sistema;

import java.util.List;
import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublica;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;


public class Sistema implements RepositorioRecetas {
	protected Sistema() {
	}
	private List<Receta> recetaConocidas;

	private static class SistemaHolder {
		private final static Sistema INSTANCE = new Sistema();
	}

	public static Sistema getInstance() {
		return SistemaHolder.INSTANCE;
	}
	
	public void agregar(Receta receta) {
		this.recetaConocidas.add(receta);
		
	}

	public void quitar(Receta receta) {
		this.recetaConocidas.remove(receta);
		
	}

	public List<Receta> listarTodas() {
		return this.recetaConocidas;
	}

	public void sugerir(Receta receta, Usuario usuario)
			throws BusinessException {

		for (String ingrediente : usuario.getAlimentosQueLeDisgustan().keySet()) {

			if (!usuario.validarSiAceptaReceta(receta)
					|| receta.contieneIngrediente(ingrediente)) {

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
		List<Receta> recetasQueConoce = null;
		recetasQueConoce = this.recetaConocidas;
		for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
			for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
				for (Receta recetasDelMiembro : miembroDelGrupo.getRecetas()) {
					if (!recetasQueConoce.contains(recetasDelMiembro)) {
						recetasQueConoce.add(recetasDelMiembro);
					}
				}
			}
		}
		return recetasQueConoce;
	}
	
}
