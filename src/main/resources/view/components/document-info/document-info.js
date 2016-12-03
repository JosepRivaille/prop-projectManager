(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.components.component:DocumentInfo
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('DocumentInfoCtrl', DocumentInfoCtrl)
        .component('documentInfo', {
            templateUrl: 'components/document-info/document-info.tpl.html',
            controller: DocumentInfoCtrl
        });

    function DocumentInfoCtrl($stateParams) {
        var vm = this;
        vm.ctrlName = 'DocumentInfoCtrl';

        vm.title = "TITLE_DOCUMENT_INFO";

        vm.document = $stateParams.documentSelected;

        alert(123);
    }

}());