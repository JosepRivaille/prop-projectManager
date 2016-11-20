(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.searcher.controller:SearcherCtrl
     *
     * @description
     *
     */
    angular
        .module('project.searcher')
        .controller('SearcherCtrl', SearcherCtrl);

    function SearcherCtrl() {
        var vm = this;
        vm.ctrlName = 'SearcherCtrl';
    }
}());