(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.settings.controller:SettingsCtrl
     *
     * @description
     *
     */
    angular
        .module('project.settings')
        .controller('SettingsCtrl', SettingsCtrl);

    function SettingsCtrl($translate, $mdThemingProvider) {
        var vm = this;
        vm.ctrlName = 'SettingsCtrl';

        vm.title = "MENU_SETTINGS";

        vm.languages = {
            title: 'SETTINGS_LANGUAGES',
            values: [
                {
                    name: 'LANG_ENGLISH',
                    flag: 'flag-eng',
                    locale: 'en_US',
                    selected: true
                },
                {
                    name: 'LANG_CATALAN',
                    flag: 'flag-cat',
                    locale: 'ca_ES',
                    selected: false
                },
                {
                    name: 'LANG_SPANISH',
                    flag: 'flag-esp',
                    locale: 'es_ES',
                    selected: false
                }
            ]
        };

        vm.theme = {
            title: 'SETTINGS_THEME',
            themeDark: 'SWITCH_THEME_DARK',
            themeLight: 'SWITCH_THEME_LIGHT',
            darkTheme: true
        };


        vm.disableOtherLanguages = function(value) {
            angular.forEach(vm.languages.values, function (language) {
                if (language.name !== value.name) {
                    language.selected = false;
                } else {
                    language.selected = true;
                    $translate.use(language.locale);
                }
            });
        };

        vm.switchTheme = function () {
            if (vm.theme.darkTheme) {
                $mdThemingProvider.theme('default')
                    .dark();
            } else {
                $mdThemingProvider.theme('default')
                    .primaryPalette('pink')
                    .accentPalette('orange');
            }
        }

    }

}());