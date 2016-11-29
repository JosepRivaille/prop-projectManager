(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.author.controller:SearchAuthorCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchAuthorCtrl', SearchAuthorCtrl);

    function SearchAuthorCtrl() {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = "TITLE_SEARCH_AUTHORS";
    }
}());