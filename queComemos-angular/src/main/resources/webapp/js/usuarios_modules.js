/**
 * Created by federico on 19/09/15.
 */
'use strict';

var usuarios_module = angular.module('queComemos-usuarios', []);

/** Usuarios Controllers **/

usuarios_module.controller('loginCtrl', function(usuarioService, $scope) {

    $scope.usuario = null;
    $scope.contraseña = null;

    this.logearUsuario = function() {

        usuarioService.postUserData($scope.usuario, $scope.contraseña, function () {

            /** Hacer el binding del service **/

        });
    };

    this.logearUsuario();

});