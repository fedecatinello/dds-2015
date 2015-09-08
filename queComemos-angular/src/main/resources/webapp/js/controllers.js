'use strict';

var app = angular.module('queComemosApp', []);

/** Controllers* */

app.controller('RecetasController', function(recetasService, $scope) {
	var self = this;
	$scope.recetas = [{'nombre' : 'Ensalada Caesar', 'autor': 'Santi', 'calorias' : 100},
					  {'nombre' : 'Pollo', 'autor': 'Fede', 'calorias' : 400}];
	$scope.recetaSelected =null;

	function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	};

	self.getRecetas = function() {
		recetasService.findAll(function(data) {
			$scope.recetas = _.map(data, transformarAReceta);
		});
	};

	$scope.setClickedRow = function(index) {
		$scope.selectedRow = index;
		$scope.recetaSelected = $scope.recetas[$scope.selectedRow];
	}


	//self.getRecetas();

});

app.controller('ContentController', function() {

	var self = this;
	self.mensajeInicio = 'Bienvenido a QueComemos';



});