(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.theme:Themes
     *
     * @description
     *
     */
    angular
        .module('project')
        .config(['$mdThemingProvider', function ($mdThemingProvider) {
            $mdThemingProvider.theme('default').dark();
        }]);
}());

