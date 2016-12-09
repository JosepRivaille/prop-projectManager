(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.author.controller:SearchBooleanCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchBooleanCtrl', SearchBooleanCtrl);

    function SearchBooleanCtrl($mdDialog) {
        var vm = this;
        vm.ctrlName = 'SearchBooleanCtrl';

        vm.title = 'MENU_SEARCH_BOOLEAN';

        vm.isDocumentSelected = false;

        (function showDialog() {
            $mdDialog.show({
                controller: DialogBooleanController,
                templateUrl: 'search/boolean/search-boolean-dialog.tpl.html',
                clickOutsideToClose: false,
                escapeToClose: false
            })}());

        function DialogBooleanController($rootScope, $scope, $mdDialog) {
            $scope.searchBoolean = function (booleanExpression) {
                try {
                    vm.isInvalidData = undefined;
                    var response = $rootScope.backendService.getDocumentsByBooleanExpression(booleanExpression);
                    vm.documentSelected = JSON.parse(response);
                    $mdDialog.hide();
                } catch (e) {
                    if (e.toString().indexOf('InvalidQueryException') !== -1) {
                        $scope.isInvalidData = 'EXCEPTION_INVALID_EXPRESSION';
                    }
                }
            }
        }

    }

}());
