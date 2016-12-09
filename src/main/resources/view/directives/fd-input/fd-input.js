(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.directives.directive:FdInput
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('FdInputCtrl', FdInputCtrl)
        .directive('fdInput', fdInput);

    function FdInputCtrl() {
        var vm = this;
        vm.ctrlName = 'FdInputCtrl';
    }

    function fdInput() {
        return {
            scope: {
                fileName: '='
            },
            link: function(scope, element) {
                element.on('change', function(evt) {
                    var files = evt.target.files;
                    alert(files[0].name);
                    alert(files[0].size);
                    alert(files[0]);

                    scope.fileName = files[0].name;
                    scope.$apply();
                });
            }
        }
    }

}());