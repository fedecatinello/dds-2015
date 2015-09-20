/**
 * Created by federico on 19/09/15.
 */

var Usuario = function() {
    this.username = null;
    this.password = null;
};

var Perfil = function() {
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

Perfil.prototype.esAlto() = function() {
    return this.imc>30;
};
Perfil.prototype.esBajo() = function() {
    return this.imc<18;
};
Perfil.prototype.esMedio() = function() {
    return this.imc>=18 && this.imc<=30;
};