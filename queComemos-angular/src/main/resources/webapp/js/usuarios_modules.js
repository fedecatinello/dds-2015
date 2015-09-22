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
        self.logearUsuario();
    };

    self.logearUsuario = function() {

        usuarioService.postUserData(self.credencials);
    };

}]);


user_profile.controller('user_profile', ['$scope', function(usuarioService, $scope) {

    var self = this;

    self.loggedUser=null;
    
//    self.nombre = null;
//    self.complexion = null;
//    self.sexo = null;
//    self.edad = null;
//    self.fechaNacimiento = null;
//    self.altura = null;
//    self.peso = null;
//    self.imc = null;
//    self.condicionesPreexistentes = [];
//    self.preferenciasAlimentarias = [];
//    self.comidasQueDisgustan = [];
    
    function transformarUsuario(jsonUsuario) {
		return Usuario.asUsuario(jsonUsuario);
	}
    
    self.getUserInfo = function() {
		usuarioService.getUserInfoByUsername(username, function() {
			self.loggedUser = transformarUsuario(jsonUsuario);
		});
	};
    

}]);