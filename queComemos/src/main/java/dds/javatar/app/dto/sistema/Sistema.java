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
		this.ingredientesCaros = new ArrayList<String>();
		this.agregarIngredientesCaros();
	}

	private static class SistemaHolder {
		private final static Sistema INSTANCE = new Sistema();
	}

	public static Sistema getInstance() {
		return SistemaHolder.INSTANCE;
	}

	@Override
	public void agregar(Receta receta) {

		this.recetaConocidas.add(receta);
		this.purificarLista();
	}

	@Override
	public void quitar(Receta receta) throws BusinessException {
		if (this.encontre(receta)) {
			this.recetaConocidas.remove(receta);
		}
	}

	private boolean encontre(Receta receta) {

		Boolean flag = false;
		for (int i = 0; i < this.recetaConocidas.size(); i++) {
			if ((this.recetaConocidas.get(i).getNombre().equals(receta.getNombre()))) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public List<Receta> listarTodas() {
		return this.recetaConocidas;
	}

	public List<String> getIngredientesCaros() {
		return this.ingredientesCaros;
	}

	public void agregarIngredienteCaro(String ingrediente) {
		this.ingredientesCaros.add(ingrediente);
	}

	public void sugerir(Receta receta, Usuario usuario) throws BusinessException {

		for (String ingrediente : receta.getIngredientes().keySet()) {

			if (!usuario.validarSiAceptaReceta(receta) || usuario.tieneAlimentoQueLeDisguste(ingrediente)) {

				throw new BusinessException("la receta: " + receta.getNombre() + " no puede ser sugerida al usuario" + usuario.getNombre());
			}

		}

	}

	public void sugerir(Receta receta, GrupoDeUsuarios grupo) throws BusinessException {
		for (String preferencia : grupo.getPreferenciasAlimenticias().keySet()) {

			if (!receta.contieneCondimento(preferencia) || !receta.contieneIngrediente(preferencia) || !(receta.getNombre() == preferencia)) {

				throw new BusinessException("La receta:" + receta.getNombre() + " no contiene palabra clave del grupo:" + grupo.getNombre());

			}
			for (Usuario integrante : grupo.getUsuarios()) {

				integrante.validarSiAceptaReceta(receta);

			}
		}
	}

	// for (Iterator iterator = recetasQueConoce.iterator(); iterator
	// .hasNext();) {
	// Receta receta = (Receta) iterator.next();
	// if (!(receta.getNombre().equals(recetasDelMiembro.getNombre()))) {
	// recetasQueConoce.add(recetasDelMiembro);
	// }

	// while(Receta recetaconocida : this.recetaConocidas){
	// if(!recetaconocida.getNombre().equals(recetasDelMiembro.getNombre())){
	// recetasQueConocePorLosMiembrosDelGrupo.add(recetasDelMiembro);
	// }
	//
	// }
	/**
	 * recetasQueConoce.toArray(); if(recetasQueConoce.indexOf(recetasDelMiembro)== -1){
	 * recetasQueConocePorLosMiembrosDelGrupo.add(recetasDelMiembro); }
	 */
	private void purificarLista() {
		for (int j = 0; j < this.recetaConocidas.size(); j++) {
			Boolean flag = false;
			for (int i = 0; i < this.recetaConocidas.size(); i++) {
				if ((this.recetaConocidas.get(i).getNombre().equals(this.recetaConocidas.get(j).getNombre()))) {
					flag = true;
				}
			}
			if (flag == true) {
				this.recetaConocidas.remove(this.recetaConocidas.get(j));
			}
		}

	}

	public List<Receta> recetasQueConoceEl(Usuario usuario) {

		this.purificarLista();
		List<Receta> recetasQueConoce = this.recetaConocidas;

		List<Receta> recetasQueConocePorLosMiembrosDelGrupo = new ArrayList<Receta>();

		if (usuario.getGruposAlQuePertenece().isEmpty() || usuario.getGruposAlQuePertenece() == null) {
			recetasQueConoce.addAll(usuario.getRecetas());
		} else {
			for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
				for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
					for (Receta recetasDelMiembro : miembroDelGrupo.getRecetas()) {

						if (!recetasQueConoce.contains(recetasDelMiembro)) {
							recetasQueConoce.add(recetasDelMiembro);
						}

						Boolean flag = false;
						for (int i = 0; i < recetasQueConoce.size(); i++) {
							if ((recetasQueConoce.get(i).getNombre().equals(recetasDelMiembro.getNombre()))) {
								flag = true;
							}
						}
						if (flag == false) {
							recetasQueConocePorLosMiembrosDelGrupo.add(recetasDelMiembro);
						}
					}
				}
			}
		}

		recetasQueConoce.addAll(recetasQueConocePorLosMiembrosDelGrupo);

		return recetasQueConoce;
	}

	private void agregarIngredientesCaros() {
		this.ingredientesCaros.add("lechon");
		this.ingredientesCaros.add("lomo");
		this.ingredientesCaros.add("salmon");
		this.ingredientesCaros.add("alcaparras");

	}

}
