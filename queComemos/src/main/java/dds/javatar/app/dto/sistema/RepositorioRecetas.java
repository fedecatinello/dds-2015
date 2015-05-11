package dds.javatar.app.dto.sistema;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublica;

public interface RepositorioRecetas {
	public void agregar(Receta receta);
	public void quitar(Receta receta);
	public List<Receta> listarTodas();

}
