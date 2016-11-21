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

    function SideNavController($rootScope, menuItems) {
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

        $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
            angular.forEach(menuItems, function (item) {
                //TODO: Remove harcoded
                if (item.state === 'project') {
                    item.selected = false;
                }
                if (item.state === fromState.name) {
                    item.selected = false;
                    if (angular.isDefined(item.collapsed)) {
                        item.collapsed = true;
                    }
                } else if (item.state === toState.name) {
                    item.selected = true;
                    if (angular.isDefined(item.collapsed)) {
                        item.collapsed = false;
                    }
                }
            });
        });
    }

}());