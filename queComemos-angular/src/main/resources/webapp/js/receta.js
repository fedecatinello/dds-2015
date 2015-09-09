var Receta = function() { 
    this.nombre = null;
    this.autor = "";
    this.dificultad = null;
    this.temporada = null;
    this.calorias = 0;
};

Receta.asReceta = function (jsonReceta) {
    return angular.extend(new Receta(), jsonReceta);
};


Receta.prototype.esPublica = function() {
    return this.autor=="";
};
