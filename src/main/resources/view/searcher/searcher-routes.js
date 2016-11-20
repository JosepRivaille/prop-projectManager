(function () {
    'use strict';

    angular
        .module('project.searcher')
        .config(config);

    function config($stateProvider) {
        $stateProvider
            .state('project.searcher', {
                url: '/searcher',
                parent: 'project',
                views: {
                    'wrapper': {
                        templateUrl: 'searcher/searcher.tpl.html',
                        controller: 'SearcherCtrl',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('project.searcher')
    }
}());

