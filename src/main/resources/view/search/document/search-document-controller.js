(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.document.controller:SearchDocumentCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchDocumentCtrl', SearchDocumentCtrl);

    function SearchDocumentCtrl($q, $timeout) {
        var vm = this;
        vm.ctrlName = 'SearchDocumentCtrl';

        vm.title = "MENU_SEARCH_SINGLE_DOCUMENT";

        vm.authors = [
            {
                name: 'Josep'
            },
            {
                name: 'Guillermo'
            },
            {
                name: 'Gabriel'
            },
            {
                name: 'Aleix'
            }
        ];

        vm.isAuthorSelected = false;

        vm.querySearch = function (inputQuery) {
            var results = inputQuery ? vm.authors.filter(createFilterFor(inputQuery)) : vm.authors, deferred;
            deferred = $q.defer();
            $timeout(function () { deferred.resolve( results ); }, Math.random() * 500, false);
            return deferred.promise;
        };

        vm.selectedItemChange = function (item) {
            vm.documentFound = {
                title: 'asd',
                author: 'asd',
                user: 'asd',
                rating: 5,
                cover: ''
            };
            vm.isAuthorSelected = true;
        };


        //////////

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(authorName) {
                var lowercaseAuthor = angular.lowercase(authorName.name);
                return lowercaseAuthor.startsWith(lowercaseQuery);
            };
        }

    }
}());