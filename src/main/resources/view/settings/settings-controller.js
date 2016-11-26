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

    function SettingsCtrl() {
        var vm = this;
        vm.ctrlName = 'SettingsCtrl';

        vm.title = "";
        vm.languages = buildLanguages();
    }

    function buildLanguages() {
        var languages = [];
        languages.push({name: 'LANG_ENGLISH', flag: 'flag-eng'});
        languages.push({name: 'LANG_CATALAN', flag: 'flag-cat'});
        languages.push({name: 'LANG_SPANISH', flag: 'flag-esp'});
        return languages;
    }

}());