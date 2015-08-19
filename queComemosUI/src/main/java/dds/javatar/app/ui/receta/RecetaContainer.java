package dds.javatar.app.ui.receta;

import java.math.BigDecimal;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.sistema.RepositorioRecetas;

public class RecetaContainer {

	private RepositorioRecetas instanceRepositorio = RepositorioRecetas.getInstance(); 
	
	private static RecetaContainer instance;
	
	public static RecetaContainer getInstance() {
		if (instance == null) {
			instance = new RecetaContainer();
			
		}
		return instance;
	}
	
	public void agregarRecetasAlRepositorio() {
		
		Receta pure = new RecetaBuilder("Pure")
        		.totalCalorias(350)
        		.agregarIngrediente("Aceite", new BigDecimal(5))
        		.agregarIngrediente("Papa", new BigDecimal(300))
        		.agregarIngrediente("Agua", new BigDecimal(100))
        		.inventadaPor("Mariano")
        		.dificultad("FACIL")
        		.temporada("Otoño")
        		.agregarCondimento("Sal", new BigDecimal(12))
				.agregarCondimento("Leche", new BigDecimal(10))
				.agregarPaso(1, "Poner el agua a hervir con la sal.")
				.agregarPaso(2, "Una vez hervida, colocar las papas.")
				.agregarPaso(3, "Retirar una vez tiernas.")
				.agregarPaso(3, "Agregar aceite, leche y sal a gusto.")
				.agregarPaso(4, "Mezclar")
				.buildReceta();
		
		instanceRepositorio.agregar(pure);

		//propia
		Receta milanesas = new RecetaBuilder("Milanesa de Pollo")
				.totalCalorias(200)
				.agregarIngrediente("Suprema", new BigDecimal(100))
				.agregarIngrediente("Huevo", new BigDecimal(80))
				.agregarIngrediente("Pan Rallado", new BigDecimal(90))
				.agregarCondimento("Laurel", new BigDecimal(20))
				.agregarPaso(1, "Rebozar las supremas con huevo revuelto y pan rallado.")
				.agregarPaso(2, "Hornear durante 25 minutos.")
				.agregarPaso(3, "Decorar con hojas de laurel.")
				.inventadaPor("ElSiscador")
				.dificultad("FACIL")
				.temporada("Todo el año")
				.buildReceta();
		
		instanceRepositorio.agregar(milanesas);

		//gente del grupo
		Receta souffleVegano= new RecetaBuilder("Souffle Vegano")
				.totalCalorias(350)
				.agregarIngrediente("Mayonesa de Girasol", new BigDecimal(300))
				.agregarIngrediente("Calabaza", new BigDecimal(80))
				.agregarIngrediente("Levadura", new BigDecimal(100))
				.agregarCondimento("Nuez moscada", new BigDecimal(10))
				.agregarPaso(1, "Hervir la calazaba hasta que se pueda deshacer con el tenedor.")
				.agregarPaso(2, "Ligar con la mayonesa de girasol, el aceite y la levadura hidratada.")
				.agregarPaso(3, "Hornear la mezcla por 15 minutos.")
				.agregarPaso(4, "Decorar con nuez moscada espolvoreada.")
				.agregarIngrediente("Aceite", new BigDecimal(50))
				.inventadaPor("Maru Botana")
				.dificultad("MEDIA")
				.temporada("Primavera")
				.buildReceta();
		
		instanceRepositorio.agregar(souffleVegano);

		//publica
		Receta tartaEspinaca = new RecetaBuilder("Tarta de Espinaca")
				.totalCalorias(340)
				.agregarIngrediente("Brotes de espinaca", new BigDecimal(80))
				.agregarIngrediente("Tapa para pascualina", new BigDecimal(2))
				.agregarIngrediente("Huevo", new BigDecimal(80))
				.agregarIngrediente("Queso Rallado", new BigDecimal(80))
				.agregarPaso(1, "Hervir la espinaca.")
				.agregarPaso(2, "Mezclar con el huevo y el queso rallado.")
				.agregarPaso(3, "Colocar en la tapa para pascualina.")
				.agregarPaso(4, "Hornear durante 40 minutos.")
				.dificultad("MEDIA")
				.inventadaPor("Cocineros Argentinos")
				.temporada("Invierno")
				.buildReceta();
		
		instanceRepositorio.agregar(tartaEspinaca);

		//publica
		Receta mousseChocolate= new RecetaBuilder("Mousse de Chocolate")
				.totalCalorias(450)
				.agregarIngrediente("Leche", new BigDecimal(300))
				.agregarIngrediente("Huevo", new BigDecimal(80))
				.agregarCondimento("Dulce de Leche Clásico", new BigDecimal(60))
				.agregarPaso(1, "Hervir la leche y desnatar.")
				.agregarPaso(2, "Agregar el huevo batido y el polvo de la gelatina sin sabor.")
				.agregarPaso(3, "Batir hasta lograr consistencia de espuma.")
				.agregarPaso(4, "Colocar en molde y dejar enfriar en heladera (Evitar el freezer).")
				.agregarPaso(5, "Servir con bordes de dulce de leche.")
				.agregarIngrediente("Chocolate", new BigDecimal(90))
				.agregarIngrediente("Gelatina sin sabor", new BigDecimal(34))
				.dificultad("FACIL")
				.temporada("Todo el año")
				.buildReceta();
		
		instanceRepositorio.agregar(mousseChocolate);

		//publica
		Receta arrolladoPrimavera= new RecetaBuilder("Arrollado Primavera")
        		.totalCalorias(320)
        		.agregarCondimento("Mayonesa", new BigDecimal(20))
        		.agregarIngrediente("Jamón", new BigDecimal(100))
        		.agregarIngrediente("Pionono", new BigDecimal(1))
        		.agregarIngrediente("Queso en Fetas", new BigDecimal(120))
        		.agregarIngrediente("Tomate", new BigDecimal(30))
        		.agregarIngrediente("Huevo Durp", new BigDecimal(80))
        		.agregarPaso(1, "Desenrollar el pionono sobre una superficie plana donde no se haje y untarlo con una fina capa de mayonesa.")
				.agregarPaso(2, "Cubrirlo con las fetas de queso y jamón.")
				.agregarPaso(3, "Cortar el tomate en juliana y agregar las tiritas.")
				.agregarPaso(4, "Enrollar con cuidado y servir frío.")
				.dificultad("MEDIA")
				.temporada("Verano")
				.buildReceta();

		instanceRepositorio.agregar(arrolladoPrimavera);

		//propia
		Receta sandwitch= new RecetaBuilder("Sanguchitos de jamón y queso")
				.totalCalorias(300)
				.agregarIngrediente("Jamón", new BigDecimal(50))
				.agregarIngrediente("Queso", new BigDecimal(80))
				.agregarIngrediente("Tomate", new BigDecimal(90))
				.agregarIngrediente("Lechuga", new BigDecimal(90))
				.agregarCondimento("Mayonesa", new BigDecimal(10))
				.agregarCondimento("Mostaza", new BigDecimal(10))
				.agregarCondimento("Ketchup", new BigDecimal(10))
				.agregarPaso(1, "Untar las dos rodajas de pan con el/los condimento(s) deseado(s).")
				.agregarPaso(2, "Agregar las fetas de jamón y queso.")
				.agregarPaso(3, "Agregar los vegetales.")
				.inventadaPor("ElSiscador")
				.dificultad("FACIL")
				.temporada("Todo el año")
				.buildReceta();
		
		instanceRepositorio.agregar(sandwitch);

		Receta ensaladaPobretona = new RecetaBuilder("Ensalada Simple")
				.totalCalorias(120)
				.agregarIngrediente("Tomate", new BigDecimal(60))
						.agregarIngrediente("Lechuga", new BigDecimal(40))
						.agregarIngrediente("Huevo", new BigDecimal(80))
						.inventadaPor("Mariano")
						.dificultad("FACIL")
						.temporada("Primavera")
						.agregarCondimento("Sal", new BigDecimal(12))
						.agregarCondimento("Aceite", new BigDecimal(10))
						.agregarPaso(1, "Cortar los vegetales.")
						.agregarPaso(2, "Hervir el huevo.")
						.agregarPaso(3, "Una vez listo, picar.")
						.agregarPaso(4, "Agregar sal y aceite a gusto.")
						.agregarPaso(5, "Mezclar")
						.buildReceta();
		
		instanceRepositorio.agregar(ensaladaPobretona);

		//propia
		Receta flan = new RecetaBuilder("Flan de Sobrecito Light")
						.totalCalorias(85)
						.agregarIngrediente("Polvo para preparar", new BigDecimal(45))
						.agregarIngrediente("Leche Descremada", new BigDecimal(1))
						.inventadaPor("ElSiscador")
						.dificultad("FACIL")
						.temporada("Primavera")
						.agregarCondimento("Dulce de Leche", new BigDecimal(30))
						.agregarCondimento("Caramelo", new BigDecimal(40))
						.agregarPaso(1, "Hervir la leche junto con la mezcla de prepararación hasta conseguir espuma.")
						.agregarPaso(2, "Pintar el molde con el caramelo.")
						.agregarPaso(3, "Colocar en el molde y dejar enfriar por 3 hs.")
						.agregarPaso(4, "Desmoldar con cuidado.")
						.agregarPaso(5, "Agregar dulce de leche en el centro.")
						.buildReceta();
		
		instanceRepositorio.agregar(flan);

		Receta papasFritas= new RecetaBuilder("Papas fritas")
       					.totalCalorias(150)
       					.agregarIngrediente("Papas", new BigDecimal(45))
       					.agregarIngrediente("Aceite", new BigDecimal(1))
       					.inventadaPor("Maru Botana")
       					.dificultad("FACIL")
       					.temporada("Todo el año")
       					.agregarCondimento("Sal", new BigDecimal(10))
       					.agregarPaso(1, "Cortar las papas en rodajas y luego en bastones.")
       					.agregarPaso(2, "Calentar el aceite en un sartén hasta que hierva.")
       					.agregarPaso(3, "Freír las papas hasta que se doren.")
       					.agregarPaso(4, "Retirar, dejar secar y enfriar.")
       					.agregarPaso(5, "Condimentar con sal a gusto.")
       					.buildReceta();
		
		instanceRepositorio.agregar(papasFritas);
	}
}
