'use strict';

var app = angular.module('queComemosApp', []);

var username = 'Maru Botana';

/** Controllers* */

app.controller('RecetasController', function(recetasService, messageService, $scope) {
	var self = this;
	self.recetas = [];
	self.recetasFavoritas = [];
	self.recetaSelected = null;
	self.esFavorita = false;

	function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	}
	;

	self.getRecetas = function() {
		recetasService.findAllByUsername(username, function(data) {
			self.recetas = _.map(data, transformarAReceta);
		});
	};

	self.getPasos = function() {
		var a = self.recetaSelected.pasosPreparacion;
		var array_values = new Array();

		for ( var key in a) {
			array_values.push(a[key]);
		}

		return array_values.join(" ").toString();
	};

	self.getRecetasFavoritas = function() {
		recetasService.findFavoritasByUsername(username, function(data) {
			self.recetasFavoritas = _.map(data, transformarAReceta);
		});
	};

	$scope.setClickedRow = function(index) {
		self.selectedRow = index;
		self.recetaSelected = self.recetas[self.selectedRow];

		self.esFavorita = self.recetasFavoritas.filter(function(obj) {
			return obj.autor == self.recetaSelected.autor;
		}).length > 0;
	}

	self.obtenerMensajeInicio = function() {
		messageService.getInitMessage(username, function(data) {
			self.mensajeInicio = data;
		}, notificarError);
	}

	function notificarError(mensaje) {
		self.getTareas();
		self.errors.push(mensaje);
		$timeout(function() {
			while (self.errors.length > 0) {
				self.errors.pop();
			}
		}, 10000);
	}

	self.obtenerMensajeInicio();
	self.getRecetas();
	self.getRecetasFavoritas();

});
