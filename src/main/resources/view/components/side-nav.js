(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.components.component:SideNav
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('SideNavController', SideNavController)
        .component('sideNav', {
            templateUrl: 'components/side-nav.html',
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