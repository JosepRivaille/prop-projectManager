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

        /*$rootScope.$watch(function () {
            return $rootScope.selectedTheme;
        }, function () {
            vm.selectedTheme = $rootScope.selectedTheme;
        });*/

//        if (angular.isUndefined(backendService)) {
            console.log('Error loading main backend service, stub activated instead');
            $rootScope.backendService = backendServiceStub;
//        } else {
  //          $rootScope.backendService = backendService;
    //    }

    }

}());