(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.home.controller:HomeCtrl
     *
     * @description
     *
     */
    angular
        .module('project.home')
        .controller('HomeCtrl', HomeCtrl);

    function HomeCtrl($rootScope) {
        var vm = this;
        vm.ctrlName = 'HomeCtrl';
        vm.recommendedDocs = JSON.parse($rootScope.backendService.getRecommendedDocs(5));
        vm.visitedDocs = JSON.parse($rootScope.backendService.getVisitedDocs(7));
    }
}());