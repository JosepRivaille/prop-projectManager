(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.controller:ManageCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('ManageCtrl', ManageCtrl);

    function ManageCtrl($rootScope, $mdDialog, $filter, $timeout, $scope) {
        var vm = this;
        vm.ctrlName = 'ManageCtrl';

        vm.title = 'MENU_MANAGEMENT_ALL';


    }
}());