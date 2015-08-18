package dds.javatar.app.ui.home;

import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.Action;

import dds.javatar.app.dto.receta.Receta;

public class HomeWindow extends SimpleWindow<Home>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HomeWindow(WindowOwner parent) {
		super(parent, new Home());
	}



	public void createContents(Panel mainPanel) {
		this.setTitle("Bienvenidos a ¿Qué Comemos?");
		new Label(mainPanel).bindValueToProperty("mensajeInicio");
		mainPanel.setLayout(new VerticalLayout());

		this.generateTable(mainPanel);

		Button view = new Button(mainPanel);
		view.setCaption("Ver");
		view.onClick(this.verReceta());
		NotNullObservable elementSelected = new NotNullObservable("recetaSelect");
		view.bindEnabled(elementSelected);


	}

	private Action verReceta() {
		// TODO Auto-generated method stub
		return null;
	}


	protected void generateTable(Panel mainPanel){
		Table<Receta> table = new Table<Receta>(mainPanel, Receta.class);
		table.setHeight(200);
		table.setWidth(450);

		this.suitableBinding(table);		
		table.bindValueToProperty("recetaSelect");

		this.fillTable(table);


	}

	protected void suitableBinding(Table<Receta> table){  
		switch(getModelObject().recetasFillType()){
		case "favoritas": table.bindItemsToProperty("recetasFav");
		break;
		case "consultas": table.bindItemsToProperty("recetasCons");
		break;
		case "top": table.bindItemsToProperty("recetasTop"); // ver si el app model va a tener recetasTop (depende de donde salgan)
		break;
		}

	}


	protected void fillTable(Table<Receta> table) {
		new Column<Receta>(table)
		.setTitle("Nombre")
		.setFixedSize(200)
		.bindContentsToProperty("nombre");
//		.bindBackground("nombre", Color.RED//TODO);

		new Column<Receta>(table) 
		.setTitle("Calorías")
		.setFixedSize(70)
		.bindContentsToProperty("calorias");

		new Column<Receta>(table) 
		.setTitle("Dificultad")
		.setFixedSize(70)
		.bindContentsToProperty("dificultad");

		new Column<Receta>(table) 
		.setTitle("Temporada")
		.setFixedSize(70)
		.bindContentsToProperty("temporada");
	}



	@Override
	protected void addActions(Panel actionsPanel) {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub
		
	}




}
