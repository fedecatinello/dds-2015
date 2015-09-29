app.service('recetasService', function($http) {

	this.findAll = function(callback) {
		$http.get('/recetas').success(callback);
	};

	this.findAllByUsername = function(username, callback, errorHandler) {
		$http.get('/recetas/' + username).success(callback);
	};

	this.findFavoritasByUsername = function(username, callback) {
		$http.get('/recetasFavoritas/' + username).success(callback);
	};

	this.updateReceta = function(receta, username, callback) {
		$http.post('/updateReceta/'+ username, receta).success(callback);
	};

	this.getIngredientes = function(patron, callback, errorHandler) {
		$http.get('/ingredientes/' + patron).success(callback);
	};

	this.buscar = function(busqueda, callback, errorHandler) {
		var url = "/recetas/buscar?";
		if (busqueda.username) url += "username=" + encodeURIComponent(busqueda.username) + "&";
		if (busqueda.nombre) url += "nombre=" + encodeURIComponent(busqueda.nombre) + "&";
		if (busqueda.caloriasDesde) url += "calorias_desde=" + encodeURIComponent(busqueda.caloriasDesde) + "&";
		if (busqueda.caloriasHasta) url += "calorias_hasta=" + encodeURIComponent(busqueda.caloriasHasta) + "&";
		if (busqueda.dificultad) url += "dificultad=" + encodeURIComponent(busqueda.dificultad) + "&";
		if (busqueda.temporada) url += "temporada=" + encodeURIComponent(busqueda.temporada) + "&";
		if (busqueda.ingrediente) url += "ingrediente=" + encodeURIComponent(busqueda.ingrediente) + "&";
		if (busqueda.aplicarFiltrosUsuario) url += "aplicar_filtros_usuario=true&";
		$http.get(url).success(callback).error(errorHandler);
	}
});

app.service('messageService', function($http) {

	this.getInitMessage = function(username, callback, errorHandler) {
		$http.get('/mensajeInicio/' + username).success(callback).error(errorHandler);
	};
});


