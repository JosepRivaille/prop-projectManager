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

    function DocumentListCtrl() {
        var vm = this;
        vm.ctrlName = 'DocumentListCtrl';
        vm.title = "TITLE_DOCUMENT_LIST";
    }

    function documentList($rootScope, $timeout, $mdDialog, $filter) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/document-list/document-list.tpl.html',
            scope: {
                documents: '=ngModel',
                isEditMode: '=?',
                parentTitle: '=?'
            },
            link: function (scope) {

                scope.isListSelected = true;
                scope.isButtonOpened = false;
                scope.tooltipVisible = false;
                scope.isDocumentSelected = false;
                scope.isCreateOrUpdate = false;

                if(angular.isUndefined(scope.isEditMode)) scope.isEditMode = false;

                function hola(){
                    alert("HOLA!");
                }

                function buildDocument() {
                    return {
                        title: '',
                        author: '',
                        cover: '',
                        content: '',
                        rating: '1'
                    }
                }

                function treatException(e) {
                    if (e.toString().indexOf('DocumentContentNotFoundException') !== -1) {
                        return 'EXCEPTION_DOCUMENT_CONTENT_NOT_FOUND';
                    } else if (e.toString().indexOf('InvalidDetailsException') !== -1) {
                        return 'EXCEPTION_INVALID_DETAILS';
                    } else if (e.toString().indexOf('AlreadyExistingDocumentException') !== -1) {
                        return 'EXCEPTION_ALREADY_EXISTING_DOCUMENT';
                    }
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

                scope.createDocument = function () {
                    scope.documentSelected = buildDocument();
                    scope.title = 'MENU_MANAGEMENT_CREATE';
                    scope.isListSelected = false;
                    scope.isNewDocument = true;
                    scope.isCreateOrUpdate = true;
                };

                scope.editDocument = function (document) {
                    scope.documentBackUp = angular.copy(document);
                    scope.documentSelected = document;
                    scope.title = 'MENU_MANAGEMENT_UPDATE';
                    scope.isListSelected = false;
                    scope.isNewDocument = false;
                    scope.isCreateOrUpdate = true;
                };

                scope.backToList = function () {
                    scope.isCreateOrUpdate = false;
                    scope.documentBackUp = undefined;
                    scope.isListSelected = true;
                    scope.title = 'MENU_MANAGEMENT_ALL';
                };

                scope.select = function(document){
                    scope.isListSelected = false;
                    scope.documentSelected = document;
                    scope.isDocumentSelected = true;

                };

                scope.back = function(){
                    scope.isDocumentSelected = false;
                    scope.isListSelected = true;
                };

                //TODO: Not working yet
                scope.export = function (document) {
                    var downloader = angular.element('#file-downloader');
                    downloader.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(document.content));
                    element.setAttribute('download', document.title + ' - ' + document.author + '.txt');
                    downloader.triggerHandler('click');
                };

                scope.storeDocument = function (event) {
                    var translations = {
                        title: $filter('translate')('DIALOG_CREATE_TITLE'),
                        textContent: $filter('translate')('DIALOG_CREATE_CONTENT'),
                        ariaLabel: $filter('translate')('DIALOG_CREATE_ARIA_LABEL'),
                        ok: $filter('translate')('DIALOG_CREATE_OK'),
                        cancel: $filter('translate')('DIALOG_CREATE_CANCEL')
                    };

                    var confirm = $mdDialog.confirm()
                        .title(translations.title)
                        .textContent(translations.textContent)
                        .ariaLabel(translations.ariaLabel)
                        .targetEvent(event)
                        .ok(translations.ok)
                        .cancel(translations.cancel);

                    $mdDialog.show(confirm).then(function() {
                        if (scope.isNewDocument) {
                            var data = JSON.stringify(scope.documentSelected);
                            try {
                                $rootScope.backendService.storeNewDocument(data);
                                scope.documents.push(scope.documentSelected);
                            } catch (e) {
                                scope.isInvalidData = treatException(e);
                            }
                        } else {
                            var newData = JSON.stringify(scope.documentSelected);
                            var oldData = JSON.stringify(scope.documentBackUp);
                            try {
                                $rootScope.backendService.updateDocument(oldData, newData);
                            } catch (e) {
                                scope.isInvalidData = treatException(e);
                            }
                        }
                        scope.isCreateOrUpdate = false;
                        scope.isListSelected = true;
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
                    }, function() {
                    });
                };



            }
        };
    }


}());