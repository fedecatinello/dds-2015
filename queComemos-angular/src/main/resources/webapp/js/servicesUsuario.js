app.service('usuarioService', function($http) {

	this.getUserInfoByUsername = function(username, callback, errorHandler) {
		$http.get('/profile/' + username).success(callback).error(errorHandler);
	};
});


/** Login Service **/

app.service('loginService', function($http){
	
this.postUserData = function(data, callback,errorHandler) {
		$http.post('/login', data).then(callback, errorHandler);
	};
});
