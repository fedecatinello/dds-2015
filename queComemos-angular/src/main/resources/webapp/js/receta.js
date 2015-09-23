var Receta = function() { 
	this.nombre = null;
	this.autor = "";
	this.dificultad = null;
	this.temporada = null;
	this.calorias = 0;
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
