package dds.javatar.app.dto.sistema;

import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.exception.BusinessException;

public interface InterfazRepositorioRecetas {
	public void agregar(Receta receta);
	public void quitar(Receta receta) throws BusinessException;
	public List<Receta> listarTodas();
	public void updateReceta(Receta receta);
	public Set<String> getAllIngredientes();
}
