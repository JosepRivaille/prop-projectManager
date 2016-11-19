(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name projectManager.controller:AppController
     *
     * @description
     *
     */
    angular
        .module('projectManager')
        .controller('AppController', AppCtrl);

    function AppCtrl($scope, $rootScope, $state, $stateParams, $timeout, $log) {
        var vm = this;
        vm.ctrlName = 'AppController';
    }

}());