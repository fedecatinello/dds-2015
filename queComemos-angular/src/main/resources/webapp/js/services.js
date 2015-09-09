app.service('recetasService', function($http) {

	this.findAll = function(callback, errorHandler) {
		$http.get('/recetas' ).success(callback);
	};

	this.findAllByUsername = function(username, callback, errorHandler) {
		$http.get('/recetas/'+ username ).success(callback);
	};

		this.findFavoritasByUsername = function(username, callback) {
		$http.get('/recetasFavoritas/'+ username ).success(callback);
	};


	this.update = function(receta, callback, errorHandler) {
		$http.put('/recetas/' + receta.id, receta).success(callback).error(
			errorHandler);
	};

	this.getInitMessage = function(callback, errorHandler){
		$http.get('/mensajeInicio').success(callback).error(errorHandler);
	};

});