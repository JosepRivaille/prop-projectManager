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

            $mdThemingProvider.theme('light');

            $mdThemingProvider.theme('dark').dark();
            
            $mdThemingProvider.theme('success-toast');
            $mdThemingProvider.theme('error-toast');

            $mdThemingProvider.setDefaultTheme('light');
            $mdThemingProvider.alwaysWatchTheme(true);
            $provide.value('themeProvider', $mdThemingProvider);
        }]);
}());
