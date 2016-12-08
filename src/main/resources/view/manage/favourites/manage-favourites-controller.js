(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.manage.controller:ManageFavouritesCtrl
     *
     * @description
     *
     */
    angular
        .module('project.manage')
        .controller('ManageFavouritesCtrl', ManageFavouritesCtrl);

    function ManageFavouritesCtrl() {
        var vm = this;
        vm.ctrlName = 'ManageFavouritesCtrl';
        vm.title = 'MENU_MANAGEMENT_FAVOURITES';
    }
}());