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

    function SettingsCtrl($translate) {
        var vm = this;
        vm.ctrlName = 'SettingsCtrl';

        vm.title = "MENU_SETTINGS";

        vm.settings = [
            {
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
            }
        ];


        vm.disableOtherLanguages = function(setting, value) {
            angular.forEach(setting.values, function (settingValue) {
                if (settingValue.name !== value.name) {
                    settingValue.selected = false;
                } else {
                    settingValue.selected = true;
                    $translate.use(settingValue.locale);
                }
            });
        };

    }

}());