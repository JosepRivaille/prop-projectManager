(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project
     *
     * @description
     *
     */
    angular
        .module('project', [
            'ui.router',
            'pascalprecht.translate',

            'project.documents'
        ])
        .run(function ($rootScope) {

        });
}());

