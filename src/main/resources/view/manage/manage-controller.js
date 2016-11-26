(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.controller:ManageCtrl
     *
     * @description
     *
     */
    angular
        .module('project.manage')
        .controller('ManageCtrl', ManageCtrl);

    function ManageCtrl() {
        var vm = this;
        vm.ctrlName = 'ManageCtrl';

        vm.title = "TITLE_ALL_DOCUMENTS";
        vm.documents = [
            {
                title: 'aaa',
                author: 'asd'
            },
            {
                title: 'aaa',
                author: 'asd'
            },
            {
                title: 'aaa',
                author: 'asd'
            },
            {
                title: 'aaa',
                author: 'asd'
            }
        ];
    }
}());