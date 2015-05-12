package dds.javatar.app.dto.sistema;

import java.util.ArrayList;

import java.util.List;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;


public class Sistema implements RepositorioRecetas {
	
	private List<Receta> recetaConocidas;
	private List<String> ingredientesCaros;
	
	
	protected Sistema() {
		this.recetaConocidas = new ArrayList<Receta>();
		this.ingredientesCaros= new ArrayList<String>();
		agregarIngredientesCaros();
	}
	
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

	public List<String> getIngredientesCaros(){
		return this.ingredientesCaros;
	}
	public void agregarIngredienteCaro(String ingrediente){
		ingredientesCaros.add(ingrediente);
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
		List<Receta> recetasQueConoce = this.recetaConocidas;
		if (usuario.getGruposAlQuePertenece().isEmpty() ||usuario.getGruposAlQuePertenece()==null) {
			recetasQueConoce.addAll(usuario.getRecetas());
		} else {
			for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
				for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
					for (Receta recetasDelMiembro : miembroDelGrupo.getRecetas()) {
						if (!recetasQueConoce.contains(recetasDelMiembro)) {
							recetasQueConoce.add(recetasDelMiembro);
						}
					}
				}
			}
		}	
		return recetasQueConoce;
	}

	private void agregarIngredientesCaros() {
		this.ingredientesCaros.add("lechon");
		this.ingredientesCaros.add("lomo");
		this.ingredientesCaros.add("salmon");
		this.ingredientesCaros.add("alcaparras");
				
		}
		
	
	
}
