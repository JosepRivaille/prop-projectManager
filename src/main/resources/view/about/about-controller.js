(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.about.controller:AboutCtrl
     *
     * @description
     *
     */
    angular
        .module('project.about')
        .controller('AboutCtrl', AboutCtrl);

    function AboutCtrl($mdDialog) {
        var vm = this;
        vm.ctrlName = 'AboutCtrl';

        vm.unlockTit = function () {
            $mdDialog.show({
                template: '<img src="../images/avatars/tit.jpg">',
                zIndex: 2000,
                clickOutsideToClose: true,
                escapeToClose: true
            });
        };
    }
}());