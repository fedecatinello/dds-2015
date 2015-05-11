package dds.javatar.app.dto.sistema;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublica;

public interface RepositorioRecetas {
	public void agregar(RecetaPublica receta);
	public void quitar(RecetaPublica receta);
	public List<Receta> listarTodas();

}
