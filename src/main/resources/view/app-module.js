(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name projectManager
     *
     * @description
     *
     */
    angular
        .module('projectManager', [
            'ui.router'
        ])
        .run(function ($rootScope) {
        })
        .directive('sideNav', function() {
            var directive = {
                restrict: 'E',
                template: "Student: <b>{{student.name}}</b> , Roll No: <b>{{student.rollno}}</b>"
            };

            directive.scope = {
                student : "=name"
            };

            directive.compile = function(element, attributes) {
                element.css("border", "1px solid #cccccc");
                return function ($scope, element, attributes) {
                    element.html("Student: <b>" + $scope.student.name + "</b> , Roll No: <b>" + $scope.student.rollno + "</b><br/>");
                    element.css("background-color", "#ff00ff");
                };
            }

            return directive;
        })
}());

