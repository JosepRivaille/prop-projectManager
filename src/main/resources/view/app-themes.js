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
        .config(['$mdThemingProvider', '$provide', function ($mdThemingProvider, $provide) {
            $mdThemingProvider.theme('dark');

            $mdThemingProvider.setDefaultTheme('dark');
            $mdThemingProvider.alwaysWatchTheme(true);
            $provide.value('themeProvider', $mdThemingProvider);
        }]);
}());

