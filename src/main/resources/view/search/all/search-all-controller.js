(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.all.controller:SearchAllCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchAllCtrl', SearchAllCtrl);

    function SearchAllCtrl() {
        var vm = this;
        vm.ctrlName = 'SearchAllCtrl';

        vm.title = 'TITLE_ALL_DOCUMENTS';

        vm.authorName = '';

        var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;
    }
}());