angular.module('recetasApp', []).controller('RecetasController', function(recetasService) {
	var self = this;
	self.recetas = [];

function transformarAReceta(jsonTarea) {
		return Receta.asReceta(jsonTarea);
	}

this.getTareas = function () {
		recetasService.findAll(function (data) {
		    self.recetas = _.map(data, transformarAReceta);
            });
	}

});

angular.module('recetasApp', []).controller('ContentController', function($scope) {

	$scope.mensajeInicio = 'Bienvenido a QueComemos';
});
