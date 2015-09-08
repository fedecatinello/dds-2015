'use strict';

var recetas_app = angular.module('recetasApp', []);

/** Controllers**/

recetas_app.controller('RecetasController', function(recetasService) {
	var self = this;
	self.recetas = [];

function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	}

this.getRecetas = function () {
		recetasService.findAll(function (data) {
		    self.recetas = _.map(data, transformarAReceta);
            });
	}

});

recetas_app.controller('ContentController', function($scope) {

	$scope.mensajeInicio = 'Bienvenido a QueComemos';
	$scope.selectedRow = null;

	$scope.setClickedRow = function(index){
		$scope.selectedRow = index;
	}
});