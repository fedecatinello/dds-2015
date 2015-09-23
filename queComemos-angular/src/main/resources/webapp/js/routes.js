'use strict';

angular.module('queComemosApp', []).config(['$routeProvider', function($routeProvider) {

	$routeProvider.when('/login', {
		templateUrl: '/login.html',
		login: true
	});
	$routeProvider.when('/index', {
		templateUrl: '/index.html',
		controller: 'MyCtrl1'
	});

	/** Agregar controller **/
	$routeProvider.when('/profile', {
		templateUrl: '/perfilUsuario.html',
		controller: 'MyCtrl2'
	});

	/** Agregar controller **/
	$routeProvider.when('/', {
		redirectTo: '/login'
	});
}]);