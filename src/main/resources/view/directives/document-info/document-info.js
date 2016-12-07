(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.directives.directive:DocumentInfo
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('DocumentInfoCtrl', DocumentInfoCtrl)
        .directive('documentInfo', documentInfo);

    function DocumentInfoCtrl() {
        var vm = this;
        vm.ctrlName = 'DocumentInfoCtrl';
        vm.title = "TITLE_DOCUMENT_INFO";
    }

    function documentInfo($rootScope) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-info/document-info.tpl.html',
            scope: {
                document: '=ngModel'
            },
            link: function (scope) {
                scope.isFavourite = $rootScope.backendService.isDocumentFavourite(scope.document.title, scope.document.author);

                scope.addFavourite = function(){
                    $rootScope.backendService.addFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                };

                scope.removeFavourite = function(){
                    $rootScope.backendService.removeFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                };
            }
        };
    }


}());