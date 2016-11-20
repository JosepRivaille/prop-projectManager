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

    function AppCtrl($scope) {
        var vm = this;
        vm.ctrlName = 'AppCtrl';

        // viewController.searchForAllDocuments(function (data) {
        //     $scope.documents = data;
        // });
    }

}());