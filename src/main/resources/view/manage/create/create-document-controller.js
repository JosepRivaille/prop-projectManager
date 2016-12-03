(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.create.controller:SearchAllCtrl
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

        vm.title = 'MENU_SEARCH_ALL';

        vm.authorName = '';

        var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;
    }
}());