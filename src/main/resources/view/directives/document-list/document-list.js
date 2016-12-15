(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.directives.directive:DocumentList
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('DocumentListCtrl', DocumentListCtrl)
        .directive('documentList', documentList);

    function DocumentListCtrl($rootScope) {
        var vm = this;
        vm.ctrlName = 'DocumentListCtrl';


    }

    function documentList($rootScope, $mdDialog, $mdToast, $filter, $timeout, $state) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-list/document-list.tpl.html',
            scope: {
                documents: '=ngModel',
                isEditMode: '=?',
                isSearchMode : '=?',
                parentTitle: '=?',
                parent: '=?'
            },
            link: function (scope) {


                updateToolbar();

                scope.importDocument = function() {
                    try {
                        var response = $rootScope.backendService.importDocument();
                        addDocumentOrdered(scope.documents, JSON.parse(response));
                        showToast('TOAST_DOCUMENT_IMPORTED_SUCCESSFULLY');
                    } catch (e) {
                        alert(e);
                        var text = treatException(e);
                        alert(text);
                        showToast(text, true);
                    }
                };

                scope.createDocument = function (){
                    $rootScope.toolbarParams.create = false;
                    $rootScope.toolbarParams.import = false;
                    scope.title = 'MENU_MANAGEMENT_CREATE';
                    scope.documentSelected = buildDocument();
                    scope.isListSelected = false;
                    scope.isNewDocument = true;
                    scope.isCreateOrUpdate = true;
                    scope.isInvalidData = undefined;
                };

                scope.searchAgain = function () {
                    updateToolbar();
                    $state.reload();
                };

                scope.back = function () {
                    scope.isDocumentSelected = false;
                    scope.isListSelected = true;
                    scope.isInvalidData = undefined;
                    updateToolbar();
                };

                $rootScope.toolbarFunctions.back = scope.back;
                $rootScope.toolbarFunctions.search = scope.searchAgain;
                $rootScope.toolbarFunctions.create = scope.createDocument;
                $rootScope.toolbarFunctions.import = scope.importDocument;



                function updateToolbar(){
                    $rootScope.toolbarParams.title = scope.parentTitle;
                    if(scope.isEditMode) {
                        $rootScope.toolbarParams.import = true;
                        $rootScope.toolbarParams.create = true;
                        $rootScope.toolbarParams.back = false;
                        $rootScope.toolbarParams.google = false;
                        $rootScope.toolbarParams.print = false;
                        $rootScope.toolbarParams.search = false;
                    }
                    else if(scope.parent=="favorites"){
                            $rootScope.toolbarParams.import = false;
                            $rootScope.toolbarParams.create = false;
                            $rootScope.toolbarParams.back = false;
                            $rootScope.toolbarParams.google = false;
                            $rootScope.toolbarParams.print = false;
                            $rootScope.toolbarParams.search = false;
                    }
                    else if(scope.isSearchMode){
                        $rootScope.toolbarParams.import = false;
                        $rootScope.toolbarParams.create = false;
                        $rootScope.toolbarParams.back = false;
                        $rootScope.toolbarParams.google = false;
                        $rootScope.toolbarParams.print = false;
                        $rootScope.toolbarParams.search = true;
                    }
                }






                scope.isDocFavourite = function(document) {
                    return $rootScope.backendService.isDocumentFavourite(document.title, document.author);
                };



                scope.editContentExternalTool = function(content){
                    scope.documentSelected.content = $rootScope.backendService.editContentExternalTool(content);
                };

                scope.removeFavourite = function (doc) {
                    $rootScope.backendService.removeFavourite(doc.title, doc.author);
                    var index = scope.documents.indexOf(doc);
                    scope.documents.splice(index, 1);
                };

                scope.isListSelected = true;
                scope.isButtonOpened = false;
                scope.tooltipVisible = false;
                scope.isDocumentSelected = false;
                scope.isCreateOrUpdate = false;

                if (angular.isUndefined(scope.isEditMode)) {
                    scope.isEditMode = false;
                }

                scope.$watch('scope.isButtonOpened', function(isOpen) {
                    if (isOpen) {
                        $timeout(function() {
                            scope.tooltipVisible = scope.isButtonOpened;
                        }, 600);
                    } else {
                        scope.tooltipVisible = scope.isButtonOpened;
                    }
                });



                scope.editDocument = function (document) {
                    scope.title = 'MENU_MANAGEMENT_UPDATE';
                    scope.documentBackUp = angular.copy(document);
                    scope.documentSelected = document;
                    scope.isListSelected = false;
                    scope.isNewDocument = false;
                    scope.isCreateOrUpdate = true;
                    scope.isInvalidData = undefined;
                };

                scope.backToList = function () {
                    var translations = {
                        title: $filter('translate')('DIALOG_BACK_TITLE'),
                        textContent: $filter('translate')('DIALOG_BACK_CONTENT'),
                        ariaLabel: $filter('translate')('DIALOG_BACK_ARIA_LABEL'),
                        ok: $filter('translate')('DIALOG_BACK_OK'),
                        cancel: $filter('translate')('DIALOG_BACK_CANCEL')
                    };

                    var confirm = $mdDialog.confirm()
                        .title(translations.title)
                        .textContent(translations.textContent)
                        .ariaLabel(translations.ariaLabel)
                        .targetEvent(event)
                        .ok(translations.ok)
                        .cancel(translations.cancel);

                    $mdDialog.show(confirm).then(function () {
                        scope.documentBackUp = undefined;
                        scope.selectedImage = undefined;
                        scope.isCreateOrUpdate = false;
                        scope.isListSelected = true;
                        $mdDialog.hide();
                    }, function () {
                    });
                };

                scope.selectDocument = function (document) {
                    scope.documentSelected = document;
                    scope.isListSelected = false;
                    scope.isDocumentSelected = true;
                };





                scope.selectImage = function () {
                    scope.selectedImage = $rootScope.backendService.selectImage();
                };


                scope.storeDocument = function (event) {
                    var translations = {
                        title: scope.isNewDocument ? $filter('translate')('DIALOG_CREATE_TITLE') :
                            $filter('translate')('DIALOG_EDIT_TITLE'),
                        textContent: scope.isNewDocument ? $filter('translate')('DIALOG_CREATE_CONTENT') :
                            $filter('translate')('DIALOG_EDIT_CONTENT'),
                        ariaLabel: scope.isNewDocument ? $filter('translate')('DIALOG_CREATE_ARIA_LABEL') :
                            $filter('translate')('DIALOG_EDIT_ARIA_LABEL'),
                        ok: scope.isNewDocument ? $filter('translate')('DIALOG_CREATE_OK') :
                            $filter('translate')('DIALOG_EDIT_OK'),
                        cancel: scope.isNewDocument ? $filter('translate')('DIALOG_CREATE_CANCEL') :
                            $filter('translate')('DIALOG_EDIT_CANCEL')
                    };

                    var confirm = $mdDialog.confirm()
                        .title(translations.title)
                        .textContent(translations.textContent)
                        .ariaLabel(translations.ariaLabel)
                        .targetEvent(event)
                        .ok(translations.ok)
                        .cancel(translations.cancel);

                    $mdDialog.show(confirm).then(function() {
                        scope.documentSelected.title = scope.documentSelected.title.capitalizeFirstLetter();
                        scope.documentSelected.author = scope.documentSelected.author.capitalizeFirstLetter();
                        if (angular.isDefined(scope.selectedImage)) {
                            scope.documentSelected.cover = scope.selectedImage;
                        }

                        if (scope.isNewDocument) {
                            var data = JSON.stringify(scope.documentSelected);
                            try {
                                var response = $rootScope.backendService.storeNewDocument(data);
                                var doc = JSON.parse(response);
                                addDocumentOrdered(scope.documents, doc);
                                showToast('TOAST_CREATED_DOCUMENT');
                                scope.isCreateOrUpdate = false;
                                scope.isDocumentSelected = false;
                                scope.isListSelected = true;
                                scope.selectedImage = undefined;
                            } catch (e) {
                                scope.isInvalidData = treatException(e);
                            }
                        } else {
                            var newData = JSON.stringify(scope.documentSelected);
                            var oldData = JSON.stringify(scope.documentBackUp);
                            try {
                                $rootScope.backendService.updateDocument(oldData, newData);
                                var index = scope.documents.indexOf(scope.documentBackUp);
                                scope.documents.splice(index, 1);
                                addDocumentOrdered(scope.documents, scope.documentSelected);
                                scope.isCreateOrUpdate = false;
                                scope.isDocumentSelected = false;
                                scope.isListSelected = true;
                                scope.selectedImage = undefined;
                                showToast('TOAST_EDITED_DOCUMENT');
                            } catch (e) {
                                scope.isInvalidData = treatException(e);
                            }
                        }
                    }, function() {
                    });

                };

                scope.showDeleteConfirm = function (event, document) {
                    var translations = {
                        title: $filter('translate')('DIALOG_DELETE_TITLE'),
                        textContent: $filter('translate')('DIALOG_DELETE_CONTENT'),
                        ariaLabel: $filter('translate')('DIALOG_DELETE_ARIA_LABEL'),
                        ok: $filter('translate')('DIALOG_DELETE_OK'),
                        cancel: $filter('translate')('DIALOG_DELETE_CANCEL')
                    };

                    var confirm = $mdDialog.confirm()
                        .title(translations.title)
                        .textContent(translations.textContent)
                        .ariaLabel(translations.ariaLabel)
                        .targetEvent(event)
                        .ok(translations.ok)
                        .cancel(translations.cancel);

                    $mdDialog.show(confirm).then(function() {
                        $rootScope.backendService.deleteDocument(document.title, document.author);
                        var index = scope.documents.indexOf(document);
                        scope.documents.splice(index, 1);
                        showToast('TOAST_DELETED_DOCUMENT');
                    }, function() {
                    });
                };

                //////////

                function buildDocument() {
                    return {
                        title: '',
                        author: '',
                        cover: '',
                        content: '',
                        relevance: '',
                        rating: 0
                    }
                }

                function getIndexToInsert(documentsList, newDocument) {
                    var minIndex = 0;
                    var maxIndex = documentsList.length - 1;
                    var currentIndex = 0;

                    while (minIndex <= maxIndex) {
                        currentIndex = Math.floor((minIndex + maxIndex) / 2);
                        if (documentsList[currentIndex].title < newDocument.title) {
                            minIndex = currentIndex + 1;
                        } else if (documentsList[currentIndex] > newDocument.title) {
                            maxIndex = currentIndex - 1;
                        } else {
                            while (documentsList[currentIndex].author < newDocument.author) {
                                ++currentIndex;
                            } return currentIndex;
                        }
                    } return currentIndex + 1;
                }

                function addDocumentOrdered(documentsList, newDocument) {
                    var index = getIndexToInsert(documentsList, newDocument);
                    documentsList.splice(index, 0, newDocument);
                }

                String.prototype.capitalizeFirstLetter = function() {
                    return this.charAt(0).toUpperCase() + this.slice(1);
                };

                function treatException(e) {
                    if (e.toString().indexOf('DocumentContentNotFoundException') !== -1) {
                        return 'EXCEPTION_DOCUMENT_CONTENT_NOT_FOUND';
                    } else if (e.toString().indexOf('InvalidDetailsException') !== -1) {
                        return 'EXCEPTION_INVALID_DETAILS';
                    } else if (e.toString().indexOf('AlreadyExistingDocumentException') !== -1) {
                        return 'EXCEPTION_ALREADY_EXISTING_DOCUMENT';
                    } else if (e.toString().indexOf('ImportExportException') !== -1) {
                        return 'EXCEPTION_IMPORT';
                    }
                    else return "GENERAL_ERROR_MESSAGE";
                }

                function showToast(toastText, error) {
                    (function() {
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent($filter('translate')(toastText))
                                .theme(error?'error-toast':'success-toast')
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
