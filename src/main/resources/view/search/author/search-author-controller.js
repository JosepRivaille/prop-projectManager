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
        vm.author = {
            name: ''
        };

        vm.authors = [
            {
                name: 'Josep',
                documents: 5
            },
            {
                name: 'Guillermo',
                documents: 4
            },
            {
                name: 'Gabriel',
                documents: 3
            },
            {
                name: 'Aleix',
                documents: 2
            }
        ];

        vm.asd = backend.test2()

    }
}());