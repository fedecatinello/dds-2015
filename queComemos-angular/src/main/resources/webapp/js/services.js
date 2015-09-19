app.service('recetasService', function($http) {

	this.findAll = function(callback, errorHandler) {
		$http.get('/recetas').success(callback);
	};

	this.findAllByUsername = function(username, callback, errorHandler) {
		$http.get('/recetas/' + username).success(callback);
	};

	this.findFavoritasByUsername = function(username, callback) {
		$http.get('/recetasFavoritas/' + username).success(callback);
	};

	this.update = function(receta, callback, errorHandler) {
		$http.put('/recetas/' + receta.id, receta).success(callback).error(errorHandler);
	};

});

app.service('messageService', function($http) {

	this.getInitMessage = function(username, callback, errorHandler) {
		$http.get('/mensajeInicio/' + username).success(callback).error(errorHandler);
	};
	
});

app.service('usuarioService', function($http){

	this.getLoggedUser = function(username, callback, errorHandler) {
		$http.get('/'+username+'/').then(callback, errorHandler);
	};
});