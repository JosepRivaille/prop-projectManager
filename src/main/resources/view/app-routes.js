(function () {
    'use strict';

    angular
        .module('productManager')
        .config(config)
        .run(['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]);

    function config($stateProvider, $urlRouterProvider) {
        $stateProvider.state('home', {
            url: '/',
            views: {
                'body': {
                    templateUrl: 'app.tpl.html'
                }
            }
        });
    }
}());
