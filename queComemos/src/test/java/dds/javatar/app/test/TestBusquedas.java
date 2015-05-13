package dds.javatar.app.test;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.receta.filtro.FiltroCondiciones;
import dds.javatar.app.dto.receta.filtro.FiltroTemplate;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class TestBusquedas extends TestRecetas{

	private Usuario usuario;
	private Busqueda busqueda;
	private RecetaPrivadaSimple recetaSimple;
	private RecetaPrivadaCompuesta recetaCompuesta;
	private Set<Receta> recetasUsuario;
	private List<FiltroTemplate> filtros;
	
	
	@Before
	public void initialize() {
		this.usuario = this.crearUsuarioBasicoValido();
		this.busqueda = new Busqueda();
		this.recetaSimple = crearRecetaPrivadaSimple();
		
		try{
			this.recetaCompuesta = crearRecetaPrivadaCompuesta();
		}catch(BusinessException e){
			e.printStackTrace();
		}
		
		this.recetasUsuario.add(recetaCompuesta);
		this.recetasUsuario.add(recetaSimple);
		
		this.busqueda.setUsuario(usuario);
	}
	
		
	@Test
	public final void testFiltroCondicionesNoFiltra() throws FilterException{
		
		FiltroTemplate filtroCondiciones = new FiltroCondiciones();
		busqueda.setFiltros(filtroCondiciones);
	}

}
