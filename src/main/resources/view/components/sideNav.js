(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name projectManager.components.component:SideNav
     *
     * @description
     *
     */
    angular
        .module('projectManager')
        .controller('SideNavController', SideNavController)
        .component('sideNav', {
            templateUrl: 'components/sideNav.html',
            controller: SideNavController
        });

    function SideNavController(menuItems) {
        var vm = this;

        vm.title = 'TITLE_PPP';

        angular.forEach(menuItems, function (item) {
            item.icon = 'fa-' + item.icon;
            if (angular.isDefined(item.children)) {
                angular.forEach(item.children, function (childItem) {
                    childItem.icon = 'fa-' + childItem.icon;
                })
            }
        });
        vm.menuItems = menuItems;
    }

}());