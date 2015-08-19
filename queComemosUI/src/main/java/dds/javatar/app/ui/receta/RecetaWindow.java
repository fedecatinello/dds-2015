package dds.javatar.app.ui.receta;

import java.math.BigDecimal;
import java.util.Map.Entry;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import com.google.gson.reflect.TypeToken;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;

public class RecetaWindow extends SimpleWindow<RecetaModel> {

	private static final long serialVersionUID = 1L;

	public RecetaWindow(WindowOwner parent) {
		super(parent, new RecetaModel());

		// TODO delete and read from the selected one.
		Receta receta = new RecetaBuilder("Ravioles").totalCalorias(350)
				.agregarIngrediente("Harina", new BigDecimal(300))
				.agregarIngrediente("Agua", new BigDecimal(70))
				.agregarIngrediente("Verdura", new BigDecimal(100))
				.agregarCondimento("Perejil", new BigDecimal(100))
				.agregarCondimento("Ajo", new BigDecimal(50))
				.inventadaPor("Santi").buildReceta();
		receta.setTemporada("Todo el año");
		receta.setDificultad("Dificl");

		this.getModelObject().setReceta(receta);
	}

	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Detalle de receta");
		super.createMainTemplate(mainPanel);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());

		this.createRecetaInfoPanel(mainPanel);

		Panel mainInfoPanel = new Panel(mainPanel);
		mainInfoPanel.setLayout(new HorizontalLayout());
		this.createLeftMainPanel(mainInfoPanel);
		this.createRightMainPanel(mainInfoPanel);
		
		this.createBottomPanel(mainPanel);
	}

	private Panel createRecetaInfoPanel(Panel mainPanel) {
		Panel recetaInfoPanel = new Panel(mainPanel);
		Label nombreReceta = new Label(recetaInfoPanel);
		nombreReceta.bindValueToProperty("receta.nombre");
		nombreReceta.setFontSize(14);

		Label caloriasReceta = new Label(recetaInfoPanel);
		caloriasReceta.bindValueToProperty("receta.calorias");
		// TODO deberia decir "350 calorias"

		// TODO agregar "si es tuya" en un panel..
		return recetaInfoPanel;
	}

	private Panel createLeftMainPanel(Panel mainInfoPanel) {
		Panel leftMainInfoPanel = new Panel(mainInfoPanel);

		Panel dificultadPanel = new Panel(leftMainInfoPanel);
		Label dificultadTitle = new Label(dificultadPanel);
		dificultadTitle.setText("Dificultad");
		dificultadTitle.setFontSize(14);
		Label dificultad = new Label(dificultadPanel);
		dificultad.bindValueToProperty("receta.dificultad");

		Panel ingredientesPanel = new Panel(leftMainInfoPanel);
		Label ingredientesTitle = new Label(ingredientesPanel);
		ingredientesTitle.setText("Ingredientes");
		ingredientesTitle.setFontSize(14);

		Table<Entry<String, BigDecimal>> tableIngredientes = new Table<Entry<String, BigDecimal>>(
				leftMainInfoPanel,
				(Class<Entry<String, BigDecimal>>) new TypeToken<Entry<String, BigDecimal>>() {
				}.getRawType());
		// TODO, arena tiene alguna clase tipo typereference ??

		tableIngredientes.setHeight(100);
		tableIngredientes.setWidth(300);
		tableIngredientes.bindItemsToProperty("receta.ingredientes.entrySet");

		new Column<Entry<String, BigDecimal>>(tableIngredientes)
				.setTitle("Dosis").setFixedSize(70)
				.bindContentsToProperty("value");
		new Column<Entry<String, BigDecimal>>(tableIngredientes)
				.setTitle("Ingrediente").setFixedSize(130)
				.bindContentsToProperty("key");

		CheckBox favorita = new CheckBox(leftMainInfoPanel);
		// TODO, setear si es favorita o no.. y poner el label!

		return leftMainInfoPanel;
	}

	private Panel createRightMainPanel(Panel mainInfoPanel) {
		Panel rightMainInfoPanel = new Panel(mainInfoPanel);

		Panel temporadaPanel = new Panel(rightMainInfoPanel);
		Label temporadaTitle = new Label(temporadaPanel);
		temporadaTitle.setText("Temporada");
		temporadaTitle.setFontSize(14);
		Label temporada = new Label(temporadaPanel);
		temporada.bindValueToProperty("receta.temporada");

		Panel condimentosPanel = new Panel(rightMainInfoPanel);
		Label condimentosTitle = new Label(condimentosPanel);
		condimentosTitle.setText("Condimentos");
		condimentosTitle.setFontSize(14);

		List<String> condimentosList = new List<String>(condimentosPanel);
		condimentosList.setHeight(70);
		condimentosList.setWidth(300);
		condimentosList.bindItemsToProperty("receta.condimentos.keySet");

		/*
		 * Panel condicionesPanel = new Panel(rightMainInfoPanel); Label
		 * condicionesTitle = new Label(condicionesPanel);
		 * condicionesTitle.setText("Condiciones Preexistentes");
		 * 
		 * List<String> condicionesList = new List<String>(condicionesPanel);
		 * condicionesList.setHeight(70); condicionesList.setWidth(300);
		 * condicionesList.bindItemsToProperty("receta.condiciones");
		 */

		return rightMainInfoPanel;
	}


	private Panel createBottomPanel(Panel mainPanel) {
		Panel bottomPanel = new Panel(mainPanel);
		
		Label title = new Label(bottomPanel);
		title.setText("Proceso de preparación");
		title.setFontSize(14);
		
		Label pasos = new Label(bottomPanel);
		// TODO bindear a los pasos haciendo un previo String.join
		return bottomPanel;
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		Button back = new Button(actionsPanel);
		back.setCaption("Volver");
		// TODO bindear para volver a la pantalla anterior.
	}

}
