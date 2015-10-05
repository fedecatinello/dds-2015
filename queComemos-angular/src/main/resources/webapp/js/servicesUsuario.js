app.service('usuarioService', function($http) {

	this.getUserInfoByUsername = function(username, callback, errorHandler) {
		$http.get('/profile/' + username).success(callback).error(errorHandler);
	};
	
	this.getUsername = function() {
		return localStorage.getItem("username");
	};

	this.logIn = function(username) {
		localStorage.setItem("username", username);
	};

	this.logOut = function() {
		localStorage.removeItem("username")
	};
});


/** Login Service **/

app.service('loginService', function($http){
	
this.postUserData = function(data, callback,errorHandler) {
		$http.post('/login', data).then(callback, errorHandler);
	};
});
