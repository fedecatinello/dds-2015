'use strict';

app.config(['$routeProvider', function($routeProvider) {

	$routeProvider.when('/login', {
		templateUrl: '/login.html',
		controller: 'LoginController'
	});
	$routeProvider.when('/index', {
		templateUrl: '/index.html',
		controller: 'MyCtrl1'
	});

	/** Agregar controller **/
	$routeProvider.when('/profile', {
		templateUrl: '/perfilUsuario.html',
		controller: 'UsuarioController'
	});

	/** Agregar controller **/
	$routeProvider.when('/', {
		redirectTo: '/index'
	});
}]);