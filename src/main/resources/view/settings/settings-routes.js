(function () {
    'use strict';

    angular
        .module('project.settings')
        .config(config);

    function config($stateProvider) {
        $stateProvider
            .state('project.settings', {
                url: '/settings',
                parent: 'project',
                abstract: true,
                views: {
                    'wrapper': {
                        templateUrl: 'settings/settings.tpl.html',
                        controller: 'SettingsCtrl',
                        controllerAs: 'vm'
                    }
                }
            })

            .state('project.settings.language', {
                url: '/language',
                parent: 'project.settings'
            })
            .state('project.settings.delete', {
                url: '/delete',
                parent: 'project.settings'
            })
            .state('project.settings.logout', {
                url: '/logout',
                parent: 'project.settings'
            });
    }
}());

