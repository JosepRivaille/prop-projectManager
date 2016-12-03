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

        vm.title = "TITLE_SEARCH_AUTHORS";

        var response = backend.getAuthorsWithPrefix("");
        alert(response);
        vm.authors = JSON.parse(response).authors;
        alert(vm.authors);

        vm.querySearch = function (query) {
            var results = query ? vm.authors.filter( createFilterFor(query) ) : vm.authors, deferred;
            deferred = $q.defer();
            $timeout(function () { deferred.resolve( results ); }, Math.random() * 100 * vm.authors.length, false);
            return deferred.promise;
        };

        //////////

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(authorName) {
                return (authorName.indexOf(lowercaseQuery) === 0);
            };

        }
    }

}());