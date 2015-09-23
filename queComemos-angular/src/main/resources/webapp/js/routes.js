'use strict';

angular.module('queComemos-usuarios', []).config(['$routeProvider', function($routeProvider) {

	$routeProvider.when('/login', {
		templateUrl: '../partials/login.html',
		login: true
	});
	$routeProvider.when('/index', {
		templateUrl: '../index.html',
		controller: 'MyCtrl1'
	});

	/** Agregar controller **/
	$routeProvider.when('/profile', {
		templateUrl: '../partials/perfilUsuario.html',
		controller: 'MyCtrl2'
	});

	/** Agregar controller **/
	$routeProvider.when('/', {
		redirectTo: '/login'
	});
}]);