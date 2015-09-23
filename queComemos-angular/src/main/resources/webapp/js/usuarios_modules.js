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

usuariosModule.controller('UsuarioController', function(usuarioService, $scope) {
	$scope.allowEdit = true;
	var self = this;

	self.loggedUser=null;
	
	self.nombre = "eliana";
	self.complexion = null;
	self.sexo = null;
	self.edad = null;
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
	
	self.getUserInfo = function() {
		usuarioService.getUserInfoByUsername(username, function() {
			self.loggedUser = transformarUsuario(jsonUsuario);
		});
	};
	
	$scope.go =  function() { 
		alert('pasaste, gil');
	};

});