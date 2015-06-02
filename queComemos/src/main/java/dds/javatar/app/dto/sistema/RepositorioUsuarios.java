package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.*;

import dds.javatar.app.dto.usuario.Usuario;

public class RepositorioUsuarios extends CollectionBasedHome<Usuario>{

	
	
	private static RepositorioUsuarios instance;

	public static RepositorioUsuarios getInstance() {
		if (instance == null) {
			instance = new RepositorioUsuarios();
		}
		return instance;
	}

	public  void add(Usuario usuario){
		effectiveCreate(usuario);
	}
	
	public void remove(Usuario usuario){
		effectiveDelete(usuario);
	}
	
	public void update(Usuario usuario){
		update(usuario);
	}
	
	public Usuario get(Usuario Usuario){
		return searchByName(Usuario).get(0);
		
	}

	public List<Usuario> searchByName(Usuario usuario){
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : super.getObjects()){
			if(usuarioEnSistema.getNombre().equals(usuario.getNombre())){
				listaUsuariosConElMismoNombre.add(usuarioEnSistema);
			}
		}
		return null;
	}
	
	public List<Usuario> list(Usuario usuario){
		List<Usuario> listaUsuariosConElMismoNombreyCondicionesPreexistentes = new ArrayList<Usuario>();
		for( Usuario usuarioConElMismoNombre : searchByName( usuario)){
			if(usuarioConElMismoNombre.getCondicionesPreexistentes().containsAll(usuario.getCondicionesPreexistentes())
					&& usuarioConElMismoNombre.getCondicionesPreexistentes().size()==usuario.getCondicionesPreexistentes().size() ){
				listaUsuariosConElMismoNombreyCondicionesPreexistentes.add(usuarioConElMismoNombre);
			}
		}
		return listaUsuariosConElMismoNombreyCondicionesPreexistentes;
	}
	@Override
	public Class<Usuario> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Usuario createExample() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Predicate getCriterio(Usuario example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
