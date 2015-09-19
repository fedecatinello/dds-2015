/**
 * Created by federico on 19/09/15.
 */

var Usuario = function() {
    this.username = null;
    this.password = null;
};

Usuario.asUsuario = function (jsonUsuario) {
    return angular.extend(new Usuario(), jsonUsuario);
};