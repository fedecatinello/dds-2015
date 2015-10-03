var Usuario = function() {
	this.username = null;
	this.password = null;
	this.nombre = null;
	this.complexion = null;
	this.sexo = null;
	this.edad = 0;
	this.fechaNacimiento = null;
	this.altura = 0;
	this.peso = 0;
	this.imc = 0;
};

Usuario.asUsuario = function (jsonUsuario) {
	return angular.extend(new Usuario(), jsonUsuario);
};
