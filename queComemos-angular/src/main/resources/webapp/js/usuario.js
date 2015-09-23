var Usuario = function() {
	this.username = null;
	this.password = null;
	this.nombre = null;
	this.complexion = null;
	this.sexo = null;
	this.edad = null;
	this.fechaNacimiento = null;
	this.altura = null;
	this.peso = null;
	this.imc = null;
};

Usuario.asUsuario = function (jsonUsuario) {
	return angular.extend(new Usuario(), jsonUsuario);
};

Usuario.prototype.esAlto = function() {
	return this.imc>30;
};

Usuario.prototype.esBajo = function() {
	return this.imc<18;
};

Usuario.prototype.esMedio = function() {
	return this.imc>=18 && this.imc<=30;
};