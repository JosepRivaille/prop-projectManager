(function () {
    'use strict';

    angular
        .module('project.manage')
        .config(config);

    function config($stateProvider) {
        $stateProvider
            .state('project.manage', {
                url: '/manage',
                parent: 'project',
                abstract: true,
                views: {
                    'wrapper': {
                        templateUrl: 'manage/manage.tpl.html',
                        controller: 'ManageCtrl',
                        controllerAs: 'vm'
                    }
                }
            })

            .state('project.manage.list', {
                url: '/list',
                parent: 'project.manage',
                views: {
                    'manage-content': {
                        templateUrl: 'manage/all/manage-all.tpl.html',
                        controller: 'ManageAllCtrl',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('project.manage.create', {
                url: '/create',
                parent: 'project.manage'
            })
            .state('project.manage.update', {
                url: '/update',
                parent: 'project.manage'
            })
            .state('project.manage.delete', {
                url: '/delete',
                parent: 'project.manage'
            })
    }
}());

