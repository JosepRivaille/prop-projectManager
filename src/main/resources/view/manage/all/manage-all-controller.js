(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.all.controller:ManageAllCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('ManageAllCtrl', ManageAllCtrl);

    function ManageAllCtrl($mdDialog, $filter) {
        var vm = this;
        vm.ctrlName = 'ManageAllCtrl';

        vm.title = 'MENU_MANAGEMENT_ALL';
        vm.isListSelected = true;

        vm.authorName = '';

        var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;

        vm.createDocument = function () {
            vm.isListSelected = false;
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
                vm.isListSelected = true;
            }, function() {
                console.log('You clicked NO!');
            });
        };

        vm.backToList = function () {
            vm.isListSelected = true;
        };

        vm.showDeleteConfirm = function (event) {
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
                console.log('You clicked ok!');
            }, function() {
                console.log('You clicked NO!');
            });
        };
    }
}());