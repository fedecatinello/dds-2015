var Receta = function() { 
	this.nombre = null;
	this.autor = "";
	this.dificultad = null;
	this.temporada = "";
	this.dificultad = "";
	this.calorias = 0;
	this.consultas = 0;
};

var Condimento = function(condimento,dosis){
	this.condimento = condimento;
	this.dosis = dosis;
};

Receta.asReceta = function (jsonReceta) {
	return angular.extend(new Receta(), jsonReceta);
};

Receta.prototype.esPublica = function() {
	return !this.autor;
};
