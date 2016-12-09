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

    function documentInfo($rootScope, $mdDialog) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-info/document-info.tpl.html',
            scope: {
                document: '=ngModel'
            },
            link: function (scope) {


                function DialogSimilarDocumentsCtrl($rootScope, $scope) {
                    $scope.searchSimilarDocuments = function (desiredNumber) {
                        try {
                            $scope.isInvalidData = undefined;
                            var documentTitle = scope.document.title;
                            var authorName = scope.document.author;
                            var response = $rootScope.backendService.getDocumentsByRelevance(documentTitle, authorName, desiredNumber);
                            $scope.similarDocuments = JSON.parse(response);
                            $scope.title = 'MENU_SEARCH_SIMILAR_DOCUMENTS';
                        } catch (e) {
                            if (e.toString().indexOf('DocumentNotFoundException') !== -1) {
                                $scope.isInvalidData = 'EXCEPTION_DOCUMENT_NOT_FOUND';
                            }
                        }
                    };
                }

                scope.isFavourite = $rootScope.backendService.isDocumentFavourite(scope.document.title, scope.document.author);

                scope.addFavourite = function () {
                    $rootScope.backendService.addFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                };

                scope.removeFavourite = function () {
                    $rootScope.backendService.removeFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                };

                scope.searchSimilars = function () {
                    $mdDialog.show({
                        controller: DialogSimilarDocumentsCtrl,
                        templateUrl: 'directives/document-info/document-info-similars-dialog.tpl.html',
                        clickOutsideToClose: true,
                        escapeToClose: true
                    });
                };

                scope.selectNewDocument = function (document) {
                    alert(document);
                    scope.document = document;
                    $mdDialog.hide();
                }
            }
        };
    }

}());
