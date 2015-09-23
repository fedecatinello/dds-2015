'use strict';
var app = angular.module('queComemosApp', ['ngAnimate', 'ui.bootstrap']);

var username = 'Maru Botana';		//Tiene Favoritos
//var username = 'ElSiscador';		//Ultimas consultas
//var username = 'Mariano';			//Ultimaas consultas



app.directive('visible', function() {
	return {
		restrict: 'A',

		link: function(scope, element, attributes) {
			scope.$watch(attributes.visible, function(value){
				element.css('visibility', value ? 'visible' : 'hidden');
			});
		}
	};
});

app.directive("dificultad", function() {
	return {
		template: '<ng-include src="getTemplateDificultad()"/>',
		//templateUrl: unfortunately has no access to $scope.user.type
		restrict: 'E',
		controller: function($scope) {
			//function used on the ng-include to resolve the template
			$scope.getTemplateDificultad = function() {
				//basic handling. It could be delegated to different Services
				if ($scope.allowEdit){
					return "partials/templateDificultadListBox.html";
				} else {
					return "partials/templateDificultadLabel.html";
				}
			}
		}
	};
});

app.directive("temporada", function() {
	return {
		template: '<ng-include src="getTemplateTemporada()"/>',
		//templateUrl: unfortunately has no access to $scope.user.type
		restrict: 'E',
		controller: function($scope) {
			//function used on the ng-include to resolve the template
			$scope.getTemplateTemporada = function() {
				//basic handling. It could be delegated to different Services
				if ($scope.allowEdit){
					return "partials/templateTemporadaListBox.html";
				} else {
					return "partials/templateTemporadaLabel.html";
				}
			}
		}
	};
});

/** Controllers* */

app.controller('ModalCtrl', function ($scope, $modalInstance, receta) {

	var newUnidadMedida = null;
	var newCantidad = null;
	var newIngrediente = null;

	$scope.ok = function () {
		receta.ingredientes[$scope.newIngrediente] = $scope.newCantidad;
		//borrar lo de abajo, en algun lado validar q se pueda agregar el ingrediente
		$modalInstance.close($scope.newUnidadMedida, $scope.newCantidad, $scope.newIngrediente);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

app.controller('RecetasController', function(recetasService, messageService, $scope, $modal) {

	var self = this;
	self.allowEdit= $scope.allowEdit = true;
	self.esFavorita = false;
	self.animationsEnabled = true;

	self.recetaSelected = null;
	self.selectedCondimento=null;
	self.selectedIngrediente = null;	
	self.recetas = [];
	self.recetasFavoritas = [];	
	self.mensajeAutorReceta;
	self.newCondimento;
	self.newDosis;
	
	self.dificultades = {
		repeatSelect: null,
		availableOptions: [
			{ id: '1', name: 'Facil' },
			{ id: '2', name: 'Medio' },
			{ id: '3', name: 'Dificil' }
		],
	};

	self.temporadas = {
		repeatSelect: null,
		availableOptions: [
			{ id: '1', name: 'Todo el año' },
			{ id: '2', name: 'Primavera' },
			{ id: '3', name: 'Verano' },
			{ id: '4', name: 'Otoño' },
			{ id: '5', name: 'Invierno' }
		],
	};

	function transformarAReceta(jsonReceta) {
		return Receta.asReceta(jsonReceta);
	}

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

	self.getMensajeAutorDeReceta = function(){
		var autor = self.recetaSelected.autor;
		if (!autor) {
			autor = 'Receta Publica';
		}
		else
		{
			autor = '   Creado por '+autor;
		}
		return autor;

	};

	$scope.setClickedReceta = function(index) {
		self.selectedRowReceta = index;
		self.recetaSelected = self.recetas[self.selectedRowReceta];

		self.esFavorita = self.recetasFavoritas.filter(function(obj) {
			return obj.autor == self.recetaSelected.autor;
		}).length > 0;

		self.mensajeAutorReceta = self.getMensajeAutorDeReceta();
	};

	$scope.setClickedCondimento = function(index) {
		self.selectedRowCondimento = index;
		self.selectedCondimento = this.Condimento;
	};

	$scope.setClickedIngrediente = function(index) {
		self.selectedRowIngrediente = index;
		self.selectedIngrediente = this.Ingrediente;
	};

	self.addCondimento = function(){
		self.recetaSelected.condimentos[self.newCondimento] = self.newDosis;
		self.newCondimento="";
		self.newDosis="";
	};

	self.deleteCondimento = function(){
		var condimentos= self.recetaSelected.condimentos;
		delete condimentos[self.selectedCondimento];
	};

	self.addIngrediente = function (size) {
		var modalInstance = $modal.open({
			animation: self.animationsEnabled,
			templateUrl: 'partials/ingredienteModal.html',
			controller: 'ModalCtrl',
			windowClass: 'modal-fit',
			resolve: {
				receta: function () {
					return self.recetaSelected;
				}
			}
		});

		modalInstance.result.then(function (newUnidadMedida, newCantidad, newIngrediente) {
			// TODO inyecto la receta seleccioanda o devuelvo los nuevos valores y asigno dsp. 
			self.result = newUnidadMedida; 
		});
	};

	self.deleteIngrediente = function(ingrediente){
		var	ingredientes= self.recetaSelected.ingredientes;
		delete ingredientes[self.selectedIngrediente];
	};

	self.obtenerMensajeInicio = function() {
		messageService.getInitMessage(username, function(data) {
			self.mensajeInicio = data;
		}, notificarError);
	};

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


app.controller("ConsultarRecetasController", function(recetasService, $timeout) {

	var self = this;

	self.busqueda = {};
	self.resultados = [];

	self.buscarRecetas = function() {
		self.busqueda.username = username;
		recetasService.buscar(self.busqueda, function(data) {
			self.resultados = _.map(data, Receta.asReceta);
		}, notificarError);
	};

	this.errors = [];

	function notificarError(mensaje) {
		self.errors.push(mensaje);
		$timeout(function() {
			while (self.errors.length > 0) {
				self.errors.pop();
			}
		}, 10000);
	};

});