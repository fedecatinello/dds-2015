'use strict';

var app = angular.module('queComemosApp', []);

//var username = 'Maru Botana';		//Tiene Favoritos
//var username = 'ElSiscador';		//Ultimas consultas
var username = 'Mariano';			//Ultimaas consultas

/** Controllers* */

app.controller('RecetasController', function(recetasService, messageService, $scope) {
	var self = this;
	self.recetas = [];
	self.recetasFavoritas = [];
	self.recetaSelected = null;
	self.esFavorita = false;
	self.mensajeAutorReceta;

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

		if (self.recetaSelected) {
			var pasos = self.recetaSelected.pasosPreparacion;	
			var array_values = new Array();

			for ( var key in pasos) {
				array_values.push(pasos[key]);
			}

			return array_values.join(" ").toString();

		};
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

		self.mensajeAutorReceta = self.getAutorDeReceta();
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

self.getAutorDeReceta = function(){
	var autor = self.recetaSelected.autor;
	if (!autor) {
		autor = 'Receta Publica'
	}
	else
	{
		autor = 'Creador por'+autor;
	}
	return autor;
			
}


	self.obtenerMensajeInicio();
	self.getRecetas();
	self.getRecetasFavoritas();

});
