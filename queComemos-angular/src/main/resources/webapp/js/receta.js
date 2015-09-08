var Receta = function() { 
    this.nombre = null;
    this.autor = null;
    this.dificultad = null;
    this.temporada = null;
    this.calorias = 0;
};

Receta.asReceta = function (jsonReceta) {
    return angular.extend(new Receta(), jsonReceta);
};


Receta.prototype.esPublica = function() {
    return this.autor.length==0;
};