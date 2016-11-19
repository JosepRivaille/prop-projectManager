(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name projectManager.locale:Locales
     *
     * @description
     *
     */
    angular
        .module('projectManager')
        .config(['$translateProvider', function ($translateProvider) {
            $translateProvider.useStaticFilesLoader({
                prefix: 'res/locales/locale-',
                suffix: '.json'
            });

            //Languages accepted: en_US, ca_ES and es_ES
            $translateProvider.preferredLanguage('en_US');
        }])
}());

