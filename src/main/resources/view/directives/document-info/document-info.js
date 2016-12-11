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

    function documentInfo($rootScope, $mdDialog, $mdToast, $filter) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-info/document-info.tpl.html',
            scope: {
                document: '=ngModel'
            },
            link: function (scope) {

                scope.isFavourite = $rootScope.backendService.isDocumentFavourite(scope.document.title, scope.document.author);

                scope.addFavourite = function () {
                    $rootScope.backendService.addFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                    showToast('TOAST_ADDED_TO_FAVOURITES');
                };

                scope.removeFavourite = function () {
                    $rootScope.backendService.removeFavourite(scope.document.title, scope.document.author);
                    scope.isFavourite ^= true;
                    showToast('TOAST_DELETED_FROM_FAVOURITES');
                };

                scope.searchSimilars = function () {
                    $mdDialog.show({
                        controller: DialogSimilarDocumentsCtrl,
                        templateUrl: 'directives/document-info/document-info-similars-dialog.tpl.html',
                        clickOutsideToClose: true,
                        escapeToClose: true
                    });
                };

                scope.export = function () {
                    var filename = scope.document.title + ' - ' + scope.document.author;
                    var data = JSON.stringify(scope.document);
                    $rootScope.backendService.exportDocument(data)
                };

                //////////

                function DialogSimilarDocumentsCtrl($rootScope, $scope, $mdDialog) {
                    $scope.searchSimilarDocuments = function (desiredNumber) {
                        try {
                            $scope.isInvalidData = undefined;
                            var documentTitle = scope.document.title;
                            var authorName = scope.document.author;
                            var response = $rootScope.backendService
                                .getDocumentsByRelevance(documentTitle, authorName, desiredNumber);
                            $scope.similarDocuments = JSON.parse(response);
                            $scope.title = 'MENU_SEARCH_SIMILAR_DOCUMENTS';
                        } catch (e) {
                            if (e.toString().indexOf('DocumentNotFoundException') !== -1) {
                                $scope.isInvalidData = 'EXCEPTION_DOCUMENT_NOT_FOUND';
                            }
                        }
                    };
                    $scope.selectNewDocument = function (document) {
                        scope.documentSelected = document;
                        $mdDialog.hide();
                    }
                }

                function showToast(toastText, error) {
                    (function() {
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent($filter('translate')(toastText))
                                .theme(error ? 'error-toast':'success-toast')
                                .position(getToastPosition())
                                .hideDelay(3000)
                        );
                    }());

                    function getToastPosition() {
                        var positions = {
                            bottom: true,
                            top: false,
                            left: false,
                            right: true
                        };
                        return Object.keys(positions).filter(function(pos) {
                            return positions[pos];
                        }).join(' ');
                    }
                }

                function download(filename, text) {
                    var element = document.createElement('a');
                    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
                    element.setAttribute('download', filename);
                    element.setAttribute('target', '_self');

                    element.style.display = 'none';
                    document.body.appendChild(element);

                    element.click();

                    document.body.removeChild(element);
                }

            }
        };
    }

}());
