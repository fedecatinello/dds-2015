'use strict';

var usuariosModule = angular.module('queComemos-usuarios', []);


/** Usuarios Controllers **/

usuariosModule.controller('login_controller', function($scope, usuarioService) {

	$scope.credentials = {
		username : 'fede',
		password : 'catinello'
	};

	$scope.submit = function(){

		alert($scope.credentials.username);
		usuarioService.postUserData($scope.credentials);
	};

});

usuariosModule.controller('UsuarioController', function($scope, usuarioService) {
	var self = this;
	
	self.nombre = "eliana";
	self.complexion = null;
	self.sexo = "F";
	self.edad = 21;
	self.fechaNacimiento = null;
	self.altura = null;
	self.peso = null;
	self.imc = null;
	self.condicionesPreexistentes = [];
	self.preferenciasAlimentarias = [];
	self.comidasQueDisgustan = [];
	self.recetasFavoritas = [];
	
	function transformarUsuario(jsonUsuario) {
		return Usuario.asUsuario(jsonUsuario);
	}
	
//	self.getUserInfo = function() {
//		usuarioService.getUserInfoByUsername(username, function() {
//			self.loggedUser = transformarUsuario(jsonUsuario);
//		});
//	};
	
	$scope.go =  function() { 
		alert('ENTRÓ AL CONTROLLER, SHORO.');
	};

});