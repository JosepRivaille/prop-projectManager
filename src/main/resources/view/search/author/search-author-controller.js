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

    function SearchAuthorCtrl($rootScope, $q, $timeout) {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = 'MENU_SEARCH_AUTHOR';

        vm.prefix = '';

        vm.searchPrefix = function () {
            try {
                var response = $rootScope.backendService.getAuthorsWithPrefix(vm.prefix);
                vm.authors = JSON.parse(response).authors;
            } catch (e) {
                if (e.toString().indexOf('AuthorNotFoundException') !== -1) {
                    vm.authors = undefined;
                }
            }
        };

        vm.selectAuthor = function(author){
            try {
                var response = $rootScope.backendService.getDocumentsByAuthorId(author.name);
                vm.documents = JSON.parse(response).documents;
            } catch (e) {
                if (e.toString().indexOf('DocumentNotFoundException') !== -1) {
                    vm.documents = undefined;
                }
            }
            vm.isAuthorSelected = true;
        };

        vm.selectDocument = function(document){
            vm.documentSelected = document;
            vm.isAuthorSelected = false;
            vm.isDocumentSelected = true;
        };

        vm.back = function () {
          vm.isAuthorSelected = true;
          vm.isDocumentSelected = false;
        };

        /* TODO: Fix if autocomplete works */
        /*var response = $rootScope.backendService.getAuthorsWithPrefix("");
        vm.authors = JSON.parse(response).authors;

        vm.querySearch = function (inputQuery) {
            var results = inputQuery ? vm.authors.filter(createFilterFor(inputQuery)) : vm.authors, deferred;
            deferred = $q.defer();
            $timeout(function () { deferred.resolve( results ); }, Math.random() * 500, false);
            return deferred.promise;
        };

        //////////

        function createFilterFor(inputQuery) {
            var lowercaseQuery = angular.lowercase(inputQuery);

            return function filterFn(author) {
                var lowercaseAuthor = angular.lowercase(author.name);
                return lowercaseAuthor.startsWith(lowercaseQuery);
            };
        }*/
    }

}());