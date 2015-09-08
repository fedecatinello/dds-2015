var Receta = function() { };

Receta.asReceta = function (jsonReceta) {
	return angular.extend(new Receta(), jsonReceta);

    this.nombre = null;
    this.dificultad = null;
    this.temporada = null;
    this.calorias = 0;
    this.autor = null;
};

Receta.prototype.cumplir = function() {
	// this.porcentajeCumplimiento = 100;
};

Receta.prototype.estaCumplida = function() {
	// return this.porcentajeCumplimiento === 100;
}