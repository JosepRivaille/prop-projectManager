(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.controller:ManageCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('ManageCtrl', ManageCtrl);

    function ManageCtrl($rootScope, $mdDialog, $filter, $timeout, $scope) {
        var vm = this;
        vm.ctrlName = 'ManageCtrl';

        vm.title = 'MENU_MANAGEMENT_ALL';
        vm.isListSelected = true;
        vm.isButtonOpened = false;
        vm.tooltipVisible = false;
        vm.isDocumentSelected = false;

        $scope.$watch('vm.isButtonOpened', function(isOpen) {
            if (isOpen) {
                $timeout(function() {
                    vm.tooltipVisible = vm.isButtonOpened;
                }, 600);
            } else {
                    vm.tooltipVisible = vm.isButtonOpened;
            }
        });

        var response = $rootScope.backendService.getCurrentUserDocuments();
        vm.documents = JSON.parse(response);

        vm.createDocument = function () {
            vm.documentSelected = buildDocument();
            vm.title = 'MENU_MANAGEMENT_CREATE';
            vm.isListSelected = false;
            vm.isNewDocument = true;
        };

        vm.editDocument = function (document) {
            vm.documentBackUp = angular.copy(document);
            vm.documentSelected = vm.documentBackUp;
            vm.title = 'MENU_MANAGEMENT_UPDATE';
            vm.isListSelected = false;
            vm.isNewDocument = false;
        };

        vm.backToList = function () {
            vm.documentBackUp = undefined;
            vm.isListSelected = true;
            vm.title = 'MENU_MANAGEMENT_ALL';
        };

        vm.select = function(document){
            vm.documentSelected = document;
            vm.isDocumentSelected = true;
        };

        vm.back = function(){
            vm.isDocumentSelected = false;
        };

        vm.storeDocument = function (event) {
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
                if (vm.isNewDocument) {
                    var data = JSON.stringify(vm.documentSelected);
                    try {
                        $rootScope.backendService.storeNewDocument(data);
                        vm.documents.push(vm.documentSelected);
                    } catch (e) {
                        vm.isInvalidData = treatException(e);
                    }
                } else {
                    var newData = JSON.stringify(vm.documentSelected);
                    var oldData = JSON.stringify(vm.documentBackUp);
                    try {
                        $rootScope.backendService.updateDocument(oldData, newData);
                    } catch (e) {
                        vm.isInvalidData = treatException(e);
                    }
                }
                vm.isListSelected = true;
            }, function() {
            });
        };

        vm.showDeleteConfirm = function (event, document) {

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
                var index = vm.documents.indexOf(document);
                vm.documents.splice(index, 1);
            }, function() {
            });
        };

        //////////

        function buildDocument() {
            return {
                title: '',
                author: '',
                cover: '',
                content: ''
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

    }
}());