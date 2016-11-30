(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.author.controller:SearchAuthorCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchAuthorCtrl', SearchAuthorCtrl);

    function SearchAuthorCtrl($mdDialog, $translate) {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = "TITLE_SEARCH_AUTHORS";

        vm.authors = {"authors":[{"name":"Miguel de Cervantes"},{"name":"Josep de Cid"}]}.authors;

        //alert(vm.authors);

        vm.doSecondaryAction = function(event) {
            $mdDialog.show(
                $mdDialog.alert()
                    .title($translate('AUTHOR_BOOKS_TITLE'))
                    .textContent($translate('AUTHOR_BOOKS_TEXT'))
                    .ariaLabel('Author click books')
                    .ok('Neat!')
                    .targetEvent(event)
            );
        };
    }

}());