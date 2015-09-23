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

	this.update = function(receta, callback, errorHandler) {
		$http.put('/recetas/' + receta.id, receta).success(callback).error(errorHandler);
	};

});

app.service('messageService', function($http) {
	
	this.getInitMessage = function(username, callback, errorHandler) {
		$http.get('/mensajeInicio/' + username).success(callback).error(errorHandler);
	};
});


/** Login Service **/

app.service('loginService', function($http){

	this.postUserData = function(data) {

		$http.post('/login?username='+data.username, data);

	};
});

app.service('usuarioService', function($http) {

	this.getUserInfoByUsername = function(username, callback) {
		$http.get('/usuarios/' + username).success(callback);
	};
});