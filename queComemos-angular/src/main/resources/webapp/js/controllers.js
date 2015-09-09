'use strict';

var app = angular.module('queComemosApp', []);

/** Controllers* */

app.controller('RecetasController', function(recetasService, $scope) {
	var self = this;
	self.recetas = [];
	self.recetaSelected =null;

	function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	};

	self.getRecetas = function() {
		recetasService.findAll(function(data) {
			self.recetas = _.map(data, transformarAReceta);
		});
	};

	self.getPasos = function() {
		var a = self.recetaSelected.pasosPreparacion;
		var array_values = new Array();

		for (var key in a) {
			array_values.push(a[key]);
		}

		return array_values.join(" ").toString();
	};

	$scope.setClickedRow = function(index) {
		self.selectedRow = index;
		self.recetaSelected = self.recetas[self.selectedRow];
	}


	self.getRecetas();

});

app.controller('ContentController', function() {

	var self = this;
	self.mensajeInicio = 'Estas son tus ultimas recetas consultadas';
	//Este mensaje depende si son ultimas consultadas, favoritas, etc



});