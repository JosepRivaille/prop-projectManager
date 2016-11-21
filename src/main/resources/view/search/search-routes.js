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
                abstract: true,
                views: {
                    'wrapper': {
                        templateUrl: 'search/search.tpl.html',
                        controller: 'SearchCtrl',
                        controllerAs: 'vm'
                    }
                }
            })

            .state('project.search.all', {
                url: '/all',
                parent: 'project.search'
            })
            .state('project.search.author', {
                url: '/author',
                parent: 'project.search'
            })
            .state('project.search.document', {
                url: '/document',
                parent: 'project.search'
            });
    }
}());

