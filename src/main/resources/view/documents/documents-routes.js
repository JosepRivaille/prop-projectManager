(function () {
    'use strict';

    angular
        .module('projectManager.documents')
        .config(config);

    function config($stateProvider) {
        $stateProvider
            .state('projectManager.documents', {
                url: '/documents',
                views: {
                    'wrapper@body': {
                        templateUrl: 'documents/documents.tpl.html',
                        controller: 'DocumentsController',
                        controllerAs: 'vm'
                    }
                }
            });
    }
}());

