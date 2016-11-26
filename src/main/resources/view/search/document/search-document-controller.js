(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.document.controller:SearchDocumentCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchDocumentCtrl', SearchDocumentCtrl);

    function SearchDocumentCtrl() {
        var vm = this;
        vm.ctrlName = 'SearchDocumentCtrl';

        vm.title = "TITLE_ALL_DOCUMENTS";
    }
}());