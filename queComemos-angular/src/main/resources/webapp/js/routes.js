/**
 * Created by federico on 19/09/15.
 */
'use strict';

angular.module('queComemos-usuarios', []).config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/login', {templateUrl: '../html/partials/login.html', login: true});
    $routeProvider.when('/index', {templateUrl: '../html/index.html', controller: 'MyCtrl1'}); /** Agregar controller **/
    $routeProvider.when('/', {redirectTo: '/index'});
}]);