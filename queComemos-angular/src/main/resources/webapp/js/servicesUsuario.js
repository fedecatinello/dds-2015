app.service('usuarioService', function($http) {

	this.getUserInfoByUsername = function(username, callback) {
		$http.get('/usuarios/' + username).success(callback);
	};
});


/** Login Service **/

app.service('loginService', function($http){

	
this.postUserData = function(data, callback,errorHandler) {
		$http.post('/login', data).then(callback, errorHandler);
	};
});
