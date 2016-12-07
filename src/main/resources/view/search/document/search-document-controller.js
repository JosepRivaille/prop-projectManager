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

    function SearchDocumentCtrl($rootScope, $mdDialog) {
        var vm = this;
        vm.ctrlName = 'SearchDocumentCtrl';

        vm.title = "MENU_SEARCH_SINGLE_DOCUMENT";

        vm.isDocumentSelected = false;
        
        (function showDialog($rootScope, event) {
            $mdDialog.show({
                controller: function ($scope) {
                    $scope.searchDocument = function (title, author) {
                        try {
                            var response = $rootScope.backendService.getDocumentByTitleAndAuthor(title, author);
                            vm.documentSelected = JSON.parse(response);
                            vm.isDocumentSelected = true;
                            $mdDialog.hide();
                        } catch (e) {
                            if (e.toString().indexOf('DocumentNotFoundException') !== -1) {
                                vm.isInvalidData = 'DocumentNotFoundException';
                            }
                            //TODO: treat not found
                        }
                    }
                },
                templateUrl: 'search/document/search-document-dialog.tpl.html',
                targetEvent: event,
                clickOutsideToClose: false,
                escapeToClose: false
            })}($rootScope));

    }
}());