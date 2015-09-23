usuariosModule.service('usuarioService', function($http) {

	this.getUserInfoByUsername = function(username, callback) {
		$http.get('/usuarios/' + username).success(callback);
	};
});