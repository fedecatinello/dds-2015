app.directive("visible", function() {
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
		restrict: 'E',
		controller: function($scope) {
			$scope.getTemplateDificultad = function() {
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
		restrict: 'E',
		controller: function($scope) {
			$scope.getTemplateTemporada = function() {
				if ($scope.allowEdit){
					return "partials/templateTemporadaListBox.html";
				} else {
					return "partials/templateTemporadaLabel.html";
				}
			}
		}
	};
});

app.directive('tablaRecetas', function() {
	return {
		templateUrl: 'templateConsultaRecetas.html'
	};
});