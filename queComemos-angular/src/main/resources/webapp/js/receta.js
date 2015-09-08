var Receta = function() { 

};

Receta.asReceta = function (jsonReceta) {
    return angular.extend(new Receta(), jsonReceta);
};


Receta.prototype.esPublica = function() {
    return this.autor.length==0;
};