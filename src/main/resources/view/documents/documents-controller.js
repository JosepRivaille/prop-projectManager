(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name projectManager.documents.controller:DocumentsController
     *
     * @description
     *
     */
    angular
        .module('projectManager.documents')
        .controller('DocumentsController', DocumentsController);

    function DocumentsController() {
        var vm = this;
        vm.ctrlName = 'DocumentsController';
    }
}());