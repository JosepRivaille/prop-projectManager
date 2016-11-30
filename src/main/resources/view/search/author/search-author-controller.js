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

    function SearchAuthorCtrl($mdDialog) {
        var vm = this;
        vm.ctrlName = 'SearchAuthorCtrl';

        vm.title = "TITLE_SEARCH_AUTHORS";

        var response = backend.getAuthorsWithPrefix('');
        vm.authors = JSON.parse(response).authors;

        vm.doSecondaryAction = function(event) {
            $mdDialog.show(
                $mdDialog.alert()
                    .title("Hey")
                    .textContent("Ho")
                    .ariaLabel('Author click books')
                    .ok('Neat!')
                    .targetEvent(event)
            );
        };
    }

}());