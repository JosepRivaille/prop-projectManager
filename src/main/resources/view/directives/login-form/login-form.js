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

    function loginForm($rootScope) {
        return {
            restrict: 'EA',
            templateUrl: 'directives/login-form/login-form.tpl.html',
            scope: {
                isLoggedIn: '=?'
            },
            link: function (scope) {
                scope.isRegister = false;

                scope.user = {
                    userName: '',
                    email: '',
                    password: '',
                    password2: ''
                };

                scope.changeFormMode = function () {
                    scope.isRegister ^= true;
                };

                scope.login = function () {
                    var email = scope.user.email;
                    var password = scope.user.password;

                    var isValidLogin = $rootScope.backendService.userLogin(email, password);
                    if (isValidLogin) {
                        scope.isLoggedIn = true;
                        $rootScope.currentUser = {
                            email: email
                        };
                        resetFormData();
                    } else {
                        //TODO: Confirmation message
                    }
                };

                scope.register = function () {
                    var userName = scope.user.userName;
                    var email = scope.user.email;
                    var password = scope.user.password;
                    var password2 = scope.user.password2;

                    var isValidRegister = $rootScope.backendService.userRegister(email, userName, password, password2);
                    if (isValidRegister) {
                        scope.isLoggedIn = true;
                        $rootScope.currentUser = {
                            email: email
                        };
                        scope.isRegister = false;
                        resetFormData();
                    } else {
                        //TODO: Confirmation message
                    }
                };
                
                function resetFormData() {
                    scope.user = {
                        userName: '',
                        email: '',
                        password: '',
                        password2: ''
                    };
                }

            }
        };
    }

}());