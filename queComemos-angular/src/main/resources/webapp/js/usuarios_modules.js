/**
 * Created by federico on 19/09/15.
 */
'use strict';

var usuarios_module = angular.module('queComemos-usuarios', []);


/** Usuarios Controllers **/

usuarios_module.controller('login_controller', ['$scope', function(usuarioService) {

    var self = this;

    self.credencials = {};

    self.submit = function(){

        alert(self.credencials);
        usuarioService.postUserData(self.credencials);
    };

}]);

usuarios_module.controller('UsuarioController', function(usuarioService, $scope) {
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