(function () {
    'use strict';

    /**
     * @ngdoc directive
     * @name project.directives.directive:LoginForm
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('LoginFormCtrl', LoginFormCtrl)
        .directive('loginForm', loginForm);

    function LoginFormCtrl() {
        var vm = this;
        vm.ctrlName = 'LoginFormCtrl';
    }

    function loginForm() {
        return {
            restrict: 'EA',
            templateUrl: 'directives/login-form/login-form.tpl.html',
            scope: {
                user: '=ngModel',
                isLoggedIn: '=?'
            },
            link: function (scope) {
                scope.isRegister = false;

                scope.changeFormMode = function () {
                    scope.isRegister = !scope.isRegister;
                };

                scope.login = function () {
                    //TODO: Call backend
                    scope.isLoggedIn = true;
                };

                scope.register = function () {
                    //TODO: Call backend
                    scope.isLoggedIn = true;
                };

            }
        };
    }

}());