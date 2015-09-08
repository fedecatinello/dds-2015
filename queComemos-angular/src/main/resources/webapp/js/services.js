app.service('recetasService', function($http) {

	this.findAll = function(callback, errorHandler) {
		$http.get('/recetas').success(callback).error(errorHandler);
	};

	this.update = function(receta, callback, errorHandler) {
		$http.put('/recetas/' + receta.id, receta).success(callback).error(
				errorHandler);
	};

});
