'use strict';

var app = angular.module('queComemosApp', []);

/** Controllers* */

app.controller('RecetasController', function(recetasService) {
	var self = this;
	self.recetas = [];
	self.recetaSelected ;

	function transformarAReceta(jsonReceta) {
		//return Receta.asReceta(jsonTarea);
		return jsonReceta.parse();
	}

	self.getRecetas = function() {
		recetasService.findAll(function(data) {
			self.recetas = _.map(data, transformarAReceta);
		});
	};

	$scope.setClickedRow = function(index) {
		self.selectedRow = index;
		self.recetaSelected = self.recetas[self.selectedRow];
	}


	


	self.getRecetas();

});

app.controller('ContentController', function($scope) {

	$scope.mensajeInicio = 'Bienvenido a QueComemos';
	$scope.selectedRow = null;

	$scope.setClickedRow = function(index) {
		$scope.selectedRow = index;
	}
});
