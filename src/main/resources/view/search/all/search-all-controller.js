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

    function SearchAllCtrl($stateParams) {
        var vm = this;
        vm.ctrlName = 'SearchAllCtrl';

        vm.title = 'MENU_SEARCH_ALL';
        vm.isDocSelected = false;

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
        ];

        vm.select = function(doc){
            $stateParams.documentSelected = doc;
            vm.isDocSelected = true;

        };
        vm.back = function(){
            vm.isDocSelected = false;
            alert(vm.isDocSelected);
        }
    }
}());