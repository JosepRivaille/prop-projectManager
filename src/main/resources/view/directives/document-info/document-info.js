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
        vm.title = "DOCUMENT_INFO";


    }

    function documentInfo($rootScope, $mdDialog, $mdToast, $filter) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-info/document-info.tpl.html',
            scope: {
                document: '=ngModel',
                from: "=?"
            },
            link: function (scope) {

                scope.searchInformation = function () {
                    $rootScope.backendService.searchInformation(scope.document.title, scope.document.author);
                }

                scope.printDocument = function () {
                    $rootScope.backendService.printDocument(scope.document.title, scope.document.author, scope.document.content);
                }

                //TOOLBAR CONFIG

                $rootScope.toolbarParams.title = scope.document.title;

                $rootScope.toolbarParams.print = true;
                $rootScope.toolbarParams.google = true;
                $rootScope.toolbarParams.import = false;
                $rootScope.toolbarParams.create = false;


                if(angular.isDefined(scope.from) && scope.from == "search"){
                    $rootScope.toolbarParams.back = false;
                    $rootScope.toolbarParams.search = true;
                }
                else{
                    $rootScope.toolbarParams.back = true;
                    $rootScope.toolbarParams.search = false;
                }
                $rootScope.toolbarFunctions.google = scope.searchInformation;
                $rootScope.toolbarFunctions.print = scope.printDocument;
                $rootScope.toolbarParams.enabled = true;

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
                    var data = JSON.stringify(scope.document);
                    try {
                        var exportedOk = $rootScope.backendService.exportDocument(data)
                        if(exportedOk) showToast('TOAST_DOCUMENT_EXPORTED_SUCCESSFULLY');
                    }catch (e) {
                        if (e.toString().indexOf('ImportExportException') !== -1) {
                            showToast('EXCEPTION_EXPORT', true);
                        }
                    }
                };

                //////////

                function DialogSimilarDocumentsCtrl($rootScope, $scope, $mdDialog) {

                    $scope.searchMode1 = 'Normal mode';
                    $scope.searchMode2 = 'Super mode';
                    $scope.searchMode = $scope.searchMode1;

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
                        scope.document = document;
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

            }
        };
    }

}());
