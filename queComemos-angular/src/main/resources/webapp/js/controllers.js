'use strict';

/** Controllers* */

app.controller('ModalCtrl', function ($scope, $modalInstance, receta) {
	var self = this;
	var newUnidadMedida = null;
	var newCantidad = null;
	var newIngrediente = null;
	self.ingredientesFiltrados=null;

/*	self.getIngredientes = function() {
		recetasService.getIngredientes(newIngrediente, function(data) {
			self.ingredientesFiltrados =data;
		});
	};
	*/
	$scope.ok = function (form) {
		if (form.$valid) {
			receta.ingredientes[$scope.newIngrediente] = $scope.newCantidad;
		//borrar lo de abajo, en algun lado validar q se pueda agregar el ingrediente
		$modalInstance.close($scope.newUnidadMedida, $scope.newCantidad, $scope.newIngrediente);	
	}
};

$scope.cancel = function () {
	$modalInstance.dismiss('cancel');
};
//self.getIngredientes();

});

app.controller('RecetasController', function(recetasService, messageService, $scope, $modal) {

	var self = this;
	self.credentials = {};
	self.credentials.username = localStorage.getItem("username");
	self.credentials.password = localStorage.getItem("password");
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
	
	function transformarAReceta(jsonReceta) {
		return Receta.asReceta(jsonReceta);
	}

	self.getRecetas = function() {
		recetasService.findAllByUsername(self.credentials.username, function(data) {
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
		recetasService.findFavoritasByUsername(self.credentials.username, function(data) {
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

	self.setClickedReceta = function(receta, index) {
		self.selectedRowReceta = index;
		self.recetaSelectedOriginal = receta;
		self.recetaSelected = jQuery.extend(true, {}, self.recetaSelectedOriginal);

		self.esFavorita = self.recetasFavoritas.filter(function(obj) {
			return obj.autor == self.recetaSelected.autor;
		}).length > 0;

		self.mensajeAutorReceta = self.getMensajeAutorDeReceta();
		self.recetaSelected.temporada == null? "" : self.recetaSelected.temporada
		self.recetaSelected.dificultad == null? "" : self.recetaSelected.dificultad
	};

	$scope.setClickedCondimento = function(index) {
		self.selectedRowCondimento = index;
		self.selectedCondimento = this.Condimento;
	};

	$scope.setClickedIngrediente = function(index) {
		self.selectedRowIngrediente = index;
		self.selectedIngrediente = this.Ingrediente;
	};

	self.addCondimento = function(form){
		if (form.$valid) {
			self.recetaSelected.condimentos[self.newCondimento] = self.newDosis;
			self.newCondimento="";
			self.newDosis="";	
		};
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

	self.updateReceta = function(){
		self.recetaSelectedOriginal = self.recetaSelected;

		recetasService.updateReceta(self.recetaSelected, self.credentials.username , function() {
			self.getRecetas();
		});
	};

	self.exit = function(){
		self.recetaSelected = self.recetaSelectedOriginal;
	};

	self.obtenerMensajeInicio = function() {
		messageService.getInitMessage(self.credentials.username, function(data) {
			self.mensajeInicio = data;
		}, notificarError);
	};

	function notificarError(mensaje) {
		//self.getTareas();
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
	
	self.credentials = {};
	self.credentials.username = localStorage.getItem("username");
	self.credentials.password = localStorage.getItem("password");

	self.busqueda = {};
	self.resultados = [];

	self.buscarRecetas = function() {
		self.busqueda.username = self.credentials.username;
		recetasService.buscar(self.busqueda, function(data) {
			self.resultados = _.map(data, Receta.asReceta);
			self.resultados.forEach(function(receta){
				receta.consultas++;
			});
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

/** Usuarios Controllers **/

app.controller('LoginController', function(loginService, $timeout, $window, $scope, $rootScope) {

	var self = this;

	self.credentials = {};
	self.errors = [];

	self.ingresar = function () {

		loginService.postUserData(self.credentials,
			function() {
				var landingUrl = "http://" + $window.location.host + "/" + "home.html";
				
				localStorage.setItem("username", self.credentials.username);
				localStorage.setItem("password", self.credentials.password);

				$window.location.href = landingUrl;
			}
			,function () {
				self.notificarError();
			});

		self.notificarError = function () {

			self.errors.push('Usuario o contraseña invalido, intente nuevamente');
			$timeout(function () {
				while (self.errors.length > 0) {
					self.errors.pop();
				}
			}, 5000);
		};
	};	
});


app.controller('UsuarioController', function ($scope, usuarioService) {
	var self = this;

	self.example = "Ver Perfil";
	self.nombre = "Eliana";
	self.complexion = null;
	self.sexo = "femenino";
	self.fechaNacimiento = null;
	self.altura = null;
	self.peso = null;
	self.imc = 22;
	self.condicionesPreexistentes = [];
	self.preferenciasAlimentarias = [];
	self.comidasQueDisgustan = [];
	self.recetasFavoritas = [];

	function transformarUsuario(jsonUsuario) {
		return Usuario.asUsuario(jsonUsuario);
	}


	self.esAlto = function(){
		return true;
	}
	
	self.esBajo = function(){
		return self.imc<18;
	}
	
	self.esMedio = function(){
		return self.imc>=18 && self.imc<=30
	}
	
	self.getUserInfo = function() {
		usuarioService.getUserInfoByUsername(self.credentials.username, function() {
			self.loggedUser = transformarUsuario(jsonUsuario);
		});
	};

});

