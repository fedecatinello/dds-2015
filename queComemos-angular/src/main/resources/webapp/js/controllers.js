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


/** Controllers* */

app.controller('ModalCtrl', function ($scope, $modalInstance, receta) {

	var newUnidadMedida=null;
	var newCantidad= null;
	var newIngrediente=null;

	$scope.ok = function () {
		receta.ingredientes[$scope.newIngrediente] = $scope.newCantidad;
		//borrar lo de abajo, en algun lado validar q se pueda agregar el ingrediente
		$modalInstance.close($scope.newUnidadMedida, $scope.newCantidad, $scope.newIngrediente);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

app.controller('RecetasController', function(recetasService, messageService, $scope,$modal) {

	var self = this;
	$scope.allowEdit = true;
	self.esFavorita = false;
	self.animationsEnabled = true;

	//Estas de aca abajo no hace falta inicializarlas, queda mas "claro". Las elimino?
	self.recetaSelected = null;
	self.selectedCondimento=null;
	self.selectedIngrediente = null;	
	self.recetas = [];
	self.recetasFavoritas = [];	
	self.mensajeAutorReceta;
	self.newCondimento;
	self.newDosis;
	

	function transformarAReceta(jsonReceta) {
		var receta= Receta.asReceta(jsonReceta);
		return receta;
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
			size: size,
			resolve: {
				receta: function () {
					return self.recetaSelected;
				}
			}
		});

		modalInstance.result.then(function (newUnidadMedida, newCantidad, newIngrediente) {
			self.result = newUnidadMedida; // TODO inyecto la receta seleccioanda,
											// o devuelvo los nuevos valores y asigno dsp. 
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
