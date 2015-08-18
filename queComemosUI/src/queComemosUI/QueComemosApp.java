package queComemosUI;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;


public class QueComemosApp extends Application{


	public static void main(String[] args) {
		new QueComemosApp().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new HomeWindow(this);
	}
	
}
