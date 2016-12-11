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

        $rootScope.selectedTheme = 'light';

        // Uncomment if wanna test in browser
        //alert('Error loading main backend service, stub activated instead');
        //$rootScope.backendService = backendServiceStub;

        $rootScope.backendService = backendService;

    }

}());