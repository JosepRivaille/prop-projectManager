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

    function ManageCtrl($mdDialog, $filter) {
        var vm = this;
        vm.ctrlName = 'ManageCtrl';

        vm.title = 'MENU_MANAGEMENT_ALL';
        vm.isListSelected = true;

        vm.authorName = '';

        /*var response = backend.searchForAllDocuments();
         vm.documents = JSON.parse(response).documents;*/

        vm.documents = [
            {
                title: 'asdasd',
                author: '123',
                user: '123123',
                cover: '',
                rating: 3
            }
        ];

        vm.createDocument = function () {
            vm.documentSelected = undefined;
            vm.title = 'MENU_MANAGEMENT_CREATE';
            vm.isListSelected = false;
            vm.isNewDocument = true;
        };

        vm.editDocument = function (document) {
            vm.documentSelected = document;
            vm.title = 'MENU_MANAGEMENT_UPDATE';
            vm.isListSelected = false;
            vm.isNewDocument = false;
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
                //TODO: Store file
                var data = JSON.stringify(vm.documentSelected);
                if (vm.isNewDocument) {
                    //backend.storeNewDocument(data);
                    var document = buildDocument(vm.documentSelected);
                    vm.documents.push(document);
                } else {
                    //backend.updateDocument(data);
                }
                vm.isListSelected = true;
            }, function() {
            });
        };

        vm.backToList = function () {
            vm.isListSelected = true;
            vm.title = 'MENU_MANAGEMENT_ALL';
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
                //TODO: Delete file
                var data = JSON.stringify(document);
                //backend.deleteDocument(data);
                var index = vm.documents.indexOf(document);
                vm.documents.splice(index, 1);
            }, function() {
            });
        };

        //////////

        function buildDocument(document) {
            return document;
        }

    }
}());