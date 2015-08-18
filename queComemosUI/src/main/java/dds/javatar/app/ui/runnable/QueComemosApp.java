package dds.javatar.app.ui.runnable;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dds.javatar.app.ui.home.HomeWindow;


public class QueComemosApp extends Application{


	public static void main(String[] args) {
		new QueComemosApp().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new HomeWindow(this);
	}
	
}
