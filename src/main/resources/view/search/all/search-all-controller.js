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

        vm.title = 'MENU_SEARCH_ALL';

        vm.authorName = '';

        /*var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;*/

        vm.documents = [
            {
                title: 'ads',
                author: 'asd',
                user: 'ads',
                rating: '5',
                cover: ''
            }
        ]

        vm.select = function(){
            alert("hola");
        }
    }
}());