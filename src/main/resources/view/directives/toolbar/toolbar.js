(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.directives.directive:Toolbar
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('ToolbarCtrl', ToolbarCtrl)
        .directive('toolbar', toolbar);

    function ToolbarCtrl() {
        var vm = this;
        vm.ctrlName = 'ToolbarCtrl';
        vm.title = "TITLE_DOCUMENT_INFO";
    }

    function toolbar($rootScope) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/toolbar/toolbar.tpl.html',
            scope: {
                document: '=ngModel',
                title: '=?'
            },
            link: function (scope) {

                    scope.isOpen = false;
                    scope.count = 0;
                    scope.selectedDirection = 'left';

            }
        };
    }

}());
