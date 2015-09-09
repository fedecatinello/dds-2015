'use strict';

var app = angular.module('queComemosApp', []);

/** Controllers* */

app.controller('RecetasController', function(recetasService, $scope) {
	var self = this;
	self.recetas = [];
	self.recetasFavoritas =[];
	self.recetaSelected =null;
	self.esFavorita=false;
	var username = 'Maru Botana'

	function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	};

	self.getRecetas = function() {
		recetasService.findAllByUsername(username,function(data) {
			self.recetas = _.map(data, transformarAReceta);
		});
	};

	self.getRecetasFavoritas = function() {
		recetasService.findFavoritasByUsername(username,function(data) {
			self.recetasFavoritas = _.map(data, transformarAReceta);
		});
	};

	$scope.setClickedRow = function(index) {
		self.selectedRow = index;
		self.recetaSelected = self.recetas[self.selectedRow];

		self.esFavorita = self.recetasFavoritas.filter(function ( obj ) {
			return obj.autor == self.recetaSelected.autor;
		}).length >0;
	}


	self.getRecetas();
	self.getRecetasFavoritas();

});

app.controller('ContentController', function() {

	var self = this;
	self.mensajeInicio = 'Estas son tus ultimas recetas consultadas';
	//Este mensaje depende si son ultimas consultadas, favoritas, etc


});