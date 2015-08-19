package dds.javatar.app.ui.runnable;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;
import org.uqbar.commons.utils.ApplicationContext;

import queComemos.entrega3.repositorio.RepoRecetas;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.ui.home.HomeWindow;
import dds.javatar.app.ui.receta.RecetaContainer;


public class QueComemosApp extends Application{


	public static void main(String[] args) {
		new QueComemosApp().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		ApplicationContext.getInstance().configureSingleton(queComemos.entrega3.dominio.Receta.class, new RepoRecetas());
		ApplicationContext.getInstance().configureSingleton(Receta.class, RepositorioRecetas.getInstance());
		RecetaContainer.getInstance().agregarRecetasAlRepositorio();
		ApplicationContext.getInstance().configureSingleton(Usuario.class, RepositorioUsuarios.getInstance());
		return new HomeWindow(this);
	}
	
}
