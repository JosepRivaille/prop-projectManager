(function () {
    'use strict';

    /*
     * @ngdoc object
     * @name project.search.author.controller:SearchAuthorCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchAuthorCtrl', SearchAuthorCtrl);

    function SearchAuthorCtrl($q, $timeout) {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = "MENU_SEARCH_AUTHOR";

        //TODO: Still problems in webView application, working properly in browser.
        /*var response = backend.getAuthorsWithPrefix("");
        vm.authors = JSON.parse(response).authors;*/

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

        vm.querySearch = function (inputQuery) {
            var results = inputQuery ? vm.authors.filter(createFilterFor(inputQuery)) : vm.authors, deferred;
            deferred = $q.defer();
            $timeout(function () { deferred.resolve( results ); }, Math.random() * 500, false);
            return deferred.promise;
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