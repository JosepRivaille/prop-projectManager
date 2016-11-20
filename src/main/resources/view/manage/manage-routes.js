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
                views: {
                    'wrapper': {
                        templateUrl: 'manage/manage.tpl.html',
                        controller: 'ManageCtrl',
                        controllerAs: 'vm'
                    }
                }
            })
    }
}());

