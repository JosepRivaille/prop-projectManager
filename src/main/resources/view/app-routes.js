(function () {
    'use strict';

    angular
        .module('project')
        .config(config)
        .run(['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams, menuItems) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]);

    function config($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('project', {
                url: '/project',
                views: {
                    'body': {
                        templateUrl: 'project.tpl.html'
                    }
                }
            });

        $urlRouterProvider.otherwise('/project');
    }

}());
