package dds.javatar.app.ui.home;

import java.awt.Color;

import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.ui.receta.RecetaWindow;

public class HomeWindow extends SimpleWindow<Home> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomeWindow(WindowOwner parent) {
		super(parent, new Home());
		this.getModelObject().loadUser();
		this.getModelObject().searchRecetas();
	}

	/** PANEL PRINCIPAL DE LA VENTANA **/

	@Override
	protected void createMainTemplate(Panel mainPanel) {

		this.setTitle("Bienvenidos a ¿Qué Comemos?");

		super.createMainTemplate(mainPanel);
	}

	/** FORMULARIO PRINCIPAL DE CONSULTA DE RECETAS **/

	@Override
	protected void createFormPanel(Panel mainPanel) {

		new Label(mainPanel).bindValueToProperty("mensajeInicio");
		mainPanel.setLayout(new VerticalLayout());

		this.generateSearchResultGrid(mainPanel);

	}

	/** ACTION BUTTONS **/

	@Override
	protected void addActions(Panel actionsPanel) {

		Button viewButton = new Button(actionsPanel);
		viewButton.setCaption("Ver");
		viewButton.onClick(() -> {
			this.getModelObject().getRecetasCons().add(this.getModelObject().getRecetaSelect());
			RecetaWindow recetaWindow = new RecetaWindow(this);
			recetaWindow.getModelObject().setReceta(this.getModelObject().getRecetaSelect());
			recetaWindow.getModelObject().setUsuarioLogeado(this.getModelObject().getUsuarioLogeado());
			recetaWindow.open();
		});
		viewButton.setForeground(Color.BLACK);
		viewButton.setBackground(Color.ORANGE);
		viewButton.bindEnabledToProperty("enableButton");
		NotNullObservable elementSelected = new NotNullObservable("recetaSelect");
		viewButton.bindEnabled(elementSelected);

	}

	protected void generateSearchResultGrid(Panel mainPanel) {
		Table<Receta> table = new Table<Receta>(mainPanel, Receta.class);
		table.setHeight(200);
		table.setWidth(550);

		this.suitableBinding(table);
		table.bindValueToProperty("recetaSelect");

		this.fillTable(table);
	}

	protected void suitableBinding(Table<Receta> table) {
		switch (this.getModelObject().recetasFillType()) {
		case "favoritas":
			table.bindItemsToProperty("recetasFav");
			break;
		case "consultas":
			table.bindItemsToProperty("recetasCons");
			break;
		case "top":
			table.bindItemsToProperty("recetasTop"); // ver si el app model va a
														// tener recetasTop
														// (depende de donde
														// salgan)
			break;
		}

	}

	protected void fillTable(Table<Receta> table) {

		Column<Receta> nombre = new Column<Receta>(table);
		// nombre.setBackground((receta) ->
		// receta.getAutor()==this.usuarioLogeado.getNombre()?
		// Color.RED:Color.BLUE)
		nombre.setTitle("Nombre");
		nombre.setFixedSize(200);
		nombre.bindContentsToProperty("nombre");
		// nombre.bindContentsToTransformer(new ColorTransformer(this.getModelObject()));

		new Column<Receta>(table).setTitle("Calorías").setFixedSize(100).bindContentsToProperty("calorias");

		new Column<Receta>(table).setTitle("Dificultad").setFixedSize(100).bindContentsToProperty("dificultad");

		new Column<Receta>(table).setTitle("Temporada").setFixedSize(100).bindContentsToProperty("temporada");
	}

}
