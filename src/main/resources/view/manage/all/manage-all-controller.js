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

    function ManageAllCtrl() {
        var vm = this;
        vm.ctrlName = 'ManageAllCtrl';

        vm.title = 'MENU_MANAGE_ALL';

        vm.authorName = '';

        /*var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;*/

        vm.showAlert = function ($event) {
            var parentElement = angular.element(document.body.wrapper);
            $mdDialog.show({
                parent: parentElement,
                targetEvent: $event,
                template: 'dialogs/delete-dialog.tpl.html',
                controller: DialogController
            });
            function DialogController($scope, $mdDialog, items) {
                $scope.items = items;
                $scope.closeDialog = function() {
                    $mdDialog.hide();
                }
            }
        }
    }
}());