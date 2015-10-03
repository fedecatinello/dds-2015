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

app.directive("authenticate", function() {
	return {
		template: '<ng-include src="getAuthenticate()"/>',
		restrict: 'E',
		controller: function($scope) {
			$scope.getAuthenticate = function() {
				if ($scope.username){
					return '<button align="right" type="button" class="btn btn-default" ng-click="{{homeCtrl.login()}}">login</button>';
				} else {
					return '<button align="right" type="button" class="btn btn-default" ng-click="{{homeCtrl.logout()}}">logout</button>';
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

app.directive("condicionalInput", function() {
	return {
		template: '<ng-include src="getTemplateInput()"/>',
		controller: function($scope) {
			$scope.getTemplateInput = function() {
				var monitoreo = localStorage.getItem("monitoreo");
				if (monitoreo === true){
					return "partials/templateCondicionalConsultasInput.html";
				} else {
					return "partials/templateCondicionalTemporadaInput.html";
				}
			}
		}
	};
});

app.directive("condicionalLabel", function() {
	return {
		template: '<ng-include src="getTemplateLabel()"/>',
		controller: function($scope) {
			$scope.getTemplateLabel = function() {
				var monitoreo = localStorage.getItem("monitoreo");
				if (monitoreo === true){
					return "partials/templateCondicionalConsultasLabel.html";
				} else {
					return "partials/templateCondicionalTemporadaLabel.html";
				}
			}
		}
	};
});