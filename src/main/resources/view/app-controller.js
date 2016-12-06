(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.controller:AppCtrl
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('AppCtrl', AppCtrl);

    function AppCtrl($rootScope) {
        var vm = this;
        vm.ctrlName = 'AppCtrl';
       // $rootScope.backendService = backendService;

    }

}());