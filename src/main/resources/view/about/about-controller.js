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

    function AboutCtrl() {
        var vm = this;
        vm.ctrlName = 'AboutCtrl';
    }
}());