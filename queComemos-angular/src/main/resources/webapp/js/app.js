'use strict';
var app = angular.module('queComemosApp', ['ngAnimate', 'ui.bootstrap','ui.router', 'LocalStorageModule']);

app.config(function($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise("/home.html");
      
      $stateProvider
      .state('Login', {
      	url: "/index.html",
      	templateUrl: "index.html",
      	data: {
      		requireLogin: false
      	}
      })

      .state('Home', {
      	url: "/home.html",
      	templateUrl: "templateConsultaRecetas.html",
      	data: {
      		requireLogin: false
      	}
      })

      .state('ConsultaRecetas', {
      	url: "/consultaRecetas.html",
      	templateUrl: "consultaRecetas.html",
      	data: {
      		requireLogin: false
      	}
      })

      .state('PerfilUsuario', {
      	url: "/perfilUsuario.html",
      	templateUrl: "perfilUsuario.html",
      	data: {
      		requireLogin: true
      	}
      })

      .state('MonitoreoConsultas', {
      	url: "/monitoreoConsultas.html",
      	templateUrl: "monitoreoConsultas.html",
      	data: {
      		requireLogin: true
      	}
      });
  });    


/*

app.run(function ($rootScope, $state, loginModal) {

	$rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
		var requireLogin = toState.data.requireLogin;

		if (requireLogin && typeof $rootScope.currentUser === 'undefined') {
			event.preventDefault();

			loginModal()
			.then(function () {
				return $state.go(toState.name, toParams);
			})
			.catch(function () {
				return $state.go('welcome');
			});
		}
	});

});
*/