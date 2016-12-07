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

    function SearchAuthorCtrl($rootScope, $mdDialog) {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = 'MENU_SEARCH_AUTHOR';

        (function showDialog($rootScope, event) {
            $mdDialog.show({
                controller: function ($scope, $mdDialog) {
                    $scope.prefix = '';
                    $scope.isPrefixSearch = false;
                    $scope.searchDocument = function (prefix) {
                        try {
                            var response = $rootScope.backendService.getAuthorsWithPrefix(prefix);
                            $scope.authors = JSON.parse(response).authors;
                            $scope.isPrefixSearch = true;
                            alert(response);
                        } catch (e) {
                            if (e.toString().indexOf('AuthorNotFoundException') !== -1) {
                                //TODO
                            }
                        }
                    };
                    $scope.selectAuthor = function (author) {
                        try {
                            var response = $rootScope.backendService.getDocumentsByAuthorId(author.name);
                            $scope.authors = JSON.parse(response).authors;
                            $scope.isDocumentSelected = true;
                            $mdDialog.hide();
                        } catch (e) {
                            if (e.toString().indexOf('AuthorNotFoundException') !== -1) {
                                //TODO
                            }
                        }
                    };
                },
                templateUrl: 'search/author/search-author-dialog.tpl.html',
                targetEvent: event,
                clickOutsideToClose: false,
                escapeToClose: false
            })}($rootScope, undefined));

        vm.searchPrefix = function () {
            vm.documents = undefined;
            vm.authors = undefined;
            try {
                var response = $rootScope.backendService.getAuthorsWithPrefix(vm.prefix);
                vm.authors = JSON.parse(response).authors;
            } catch (e) {
                if (e.toString().indexOf('AuthorNotFoundException') !== -1) {
                    //TODO
                }
            }
        };

        vm.selectAuthor = function(author){
            vm.documents = undefined;
            try {
                var response = $rootScope.backendService.getDocumentsByAuthorId(author.name);
                vm.documents = JSON.parse(response).documents;
            } catch (e) {
                if (e.toString().indexOf('DocumentNotFoundException') !== -1) {
                    //TODO
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

    }

}());