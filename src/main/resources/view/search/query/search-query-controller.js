(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.author.controller:SearchQueryCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchQueryCtrl', SearchQueryCtrl);

    function SearchQueryCtrl($mdDialog) {
        var vm = this;
        vm.ctrlName = 'SearchQueryCtrl';

        vm.title = 'MENU_SEARCH_QUERY';

        vm.isDocumentSelected = false;

        (function showDialog() {
            $mdDialog.show({
                controller: DialogBooleanController,
                templateUrl: 'search/query/search-query-dialog.tpl.html',
                clickOutsideToClose: false,
                escapeToClose: false
            })}());

        function DialogBooleanController($rootScope, $scope, $mdDialog) {
            $scope.searchQuery = function (query) {
                alert("entramos en busqueda query");
                try {
                    vm.isInvalidData = undefined;
                    var response = $rootScope.backendService.getDocumentsByQuery(query);
                    vm.documentSelected = JSON.parse(response);
                    $mdDialog.hide();
                } catch (e) {
                    alert(e);
                    if (e.toString().indexOf('InvalidQueryException') !== -1) {
                        $scope.isInvalidData = 'EXCEPTION_INVALID_QUERY';
                    }
                }
            }
        }

    }

}());
