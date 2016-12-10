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

            $mdThemingProvider.theme('light')
                .primaryPalette('blue')
                .accentPalette('indigo')
                .warnPalette('red')
                .backgroundPalette('light-green');

            $mdThemingProvider.theme('dark')
                .primaryPalette('amber')
                .accentPalette('deep-purple')
                .warnPalette('teal')
                .backgroundPalette('blue-grey');
            
            $mdThemingProvider.theme('success-toast');
            $mdThemingProvider.theme('error-toast');

            $mdThemingProvider.setDefaultTheme('light');

            $mdThemingProvider.alwaysWatchTheme(true);
            $provide.value('themeProvider', $mdThemingProvider);
        }]);
}());

