'use strict';

var app = angular.module('queComemosApp', []);

/** Controllers* */

app.controller('RecetasController', function(recetasService) {
	var self = this;
	self.recetas = [];
	self.mensajeInicio = 'lalalal';

	function transformarAReceta(jsonReceta) {
		//return Receta.asReceta(jsonTarea);
		return jsonReceta.parse();
	}

	this.getRecetas = function() {
		recetasService.findAll(function(data) {
			self.recetas = _.map(data, transformarAReceta);
		});
		return self.recetas;
	}

});

app.controller('ContentController', function($scope) {

	$scope.mensajeInicio = 'Bienvenido a QueComemos';
	$scope.selectedRow = null;

	$scope.setClickedRow = function(index) {
		$scope.selectedRow = index;
	}
});
