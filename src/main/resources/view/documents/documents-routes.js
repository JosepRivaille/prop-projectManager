(function () {
    'use strict';

    angular
        .module('projectManager.documents')
        .config(config);

    function config($stateProvider) {
        console.log(1212341453);
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

