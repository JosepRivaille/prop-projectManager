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

    function SettingsCtrl($rootScope, $translate, $state) {
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
                    selected: false
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

        angular.forEach(vm.languages.values, function (language) {
            language.selected = (language.locale === $translate.use());
        });

        vm.theme = {
            title: 'SETTINGS_THEME',
            themeDark: 'SWITCH_THEME_DARK',
            themeLight: 'SWITCH_THEME_LIGHT',
            darkTheme: true
        };

        vm.disableOtherLanguages = function(value) {
            angular.forEach(vm.languages.values, function (language) {
                language.selected = (language.name === value.name);
            });
            $translate.use(value.locale);
        };

        vm.switchTheme = function () {
            var theme = vm.darkTheme ? 'dark' : 'light';
            themeProvider.reload(theme);
        };

        vm.logout = function () {
            $rootScope.backendService.userLogout();
            $state.go('project');
        }

    }

}());