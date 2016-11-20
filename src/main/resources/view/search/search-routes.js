(function () {
    'use strict';

    angular
        .module('project.search')
        .config(config);

    function config($stateProvider) {
        $stateProvider
            .state('project.search', {
                url: '/search',
                parent: 'project',
                views: {
                    'wrapper': {
                        templateUrl: 'search/search.tpl.html',
                        controller: 'SearchCtrl',
                        controllerAs: 'vm'
                    }
                }
            })
    }
}());

