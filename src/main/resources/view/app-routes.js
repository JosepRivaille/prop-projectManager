(function () {
    'use strict';

    angular
        .module('projectManager')
        .config(config)
        .run(['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]);

    function config($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('projectManager', {
                url: '',
                views: {
                    'body': {
                        templateUrl: 'app.tpl.html'
                    }
                }
            });


        $urlRouterProvider.otherwise('');
    }

}());
