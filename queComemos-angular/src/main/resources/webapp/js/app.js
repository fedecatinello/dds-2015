'use strict';
var app = angular.module('queComemosApp', ['ngAnimate', 'ui.bootstrap','ui.router', 'LocalStorageModule']);

app.config(function($stateProvider, $urlRouterProvider){

      $stateProvider
      .state('Home', {
      	url: "/home.html",
      	templateUrl: "templateConsultaRecetas.html",
            controller: "RecetasController as Ctrl",
            data: {
                requireLogin: true
          }
    })

      .state('userDeslogueado', {
            url: "/userDeslogueado.html",
            templateUrl: "userDeslogueado.html",
            controller: "HomeController as homeCtrl", 
            data: {
                requireLogin: false
          }
    })

      .state('ConsultaRecetas', {
      	url: "/consultaRecetas.html",
      	templateUrl: "consultaRecetas.html",
            controller: "ConsultarRecetasController as Ctrl",
            data: {
                requireLogin: true
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
           controller: "ConsultarRecetasController as Ctrl",
           data: {
              requireLogin: true
        }
  });
});    




app.run(function ($rootScope, $state, loginModal) {

	$rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
		var requireLogin = toState.data.requireLogin;

		if (requireLogin && (localStorage.getItem("username") == null || localStorage.getItem("username") == 'null')) {
			event.preventDefault();

			loginModal()
			.then(function () {
				return $state.go(toState.name, toParams);
			})
			.catch(function () {
			//	return $state.go('Home');
			});
		}
	});

});

