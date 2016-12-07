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
            templateUrl: 'components/side-nav/side-nav.tpl.html',
            controller: SideNavController
        });

    function SideNavController($rootScope, $mdDialog, menuItems) {
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

        $rootScope.$on('$stateChangeStart', function(event, toState) {

            $mdDialog.hide();

            angular.forEach(menuItems, function (item) {
                item.selected = item.state === toState.name;
                item.collapsed = true;
                if (angular.isDefined(item.children)) {
                    angular.forEach(item.children, function (child) {
                        if (child.state === toState.name) {
                            item.selected = true;
                            item.collapsed = false;
                            child.selected = true;
                        } else {
                            child.selected = false;
                        }
                    });
                }
            });
        });
    }

}());