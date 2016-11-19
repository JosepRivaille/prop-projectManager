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
        .controller('AppController', AppController);

    function AppController() {
        var vm = this;
        vm.ctrlName = 'AppController';
    }

}());