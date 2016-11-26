(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.controller:SearchCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchCtrl', SearchCtrl);

    function SearchCtrl() {
        var vm = this;
        vm.ctrlName = 'SearchCtrl';

        vm.title = "TITLE_ALL_DOCUMENTS";
    }
}());