(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.directives.directive:FormFileUpload
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('FormFileUploadCtrl', FormFileUploadCtrl)
        .directive('formFileUpload', formFileUpload);

    function FormFileUploadCtrl() {
        var vm = this;
        vm.ctrlName = 'StarRatingCtrl';
    }

    function formFileUpload() {
        return {
            restrict: 'EA',
            templateUrl: 'directives/form-file-upload/form-file-upload.tpl.html',
            link: apsUploadFileLink
        };
    }

    function apsUploadFileLink(scope, element) {
        var input = $(element[0].querySelector('#fileInput'));
        var button = $(element[0].querySelector('#uploadButton'));
        var textInput = $(element[0].querySelector('#textInput'));

        if (input.length && button.length && textInput.length) {
            button.click(function (e) {
                input.click();
            });
            textInput.click(function (e) {
                input.click();
            });
        }

        input.on('change', function (e) {
            var files = e.target.files;
            if (files[0]) {
                scope.fileName = files[0].name;
            } else {
                scope.fileName = null;
            }
            scope.$apply();
        });
    }

}());