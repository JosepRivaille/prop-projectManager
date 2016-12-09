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
            scope: {},
            link: function (scope) {

                scope.isLoggedIn = false;
                scope.isRegister = false;

                scope.user = {
                    userName: '',
                    email: '',
                    password: '',
                    password2: ''
                };

                scope.isInvalidData = undefined;

                scope.$watch(function() {
                    return $rootScope.isLoggedIn;
                }, function() {
                    scope.isLoggedIn = $rootScope.isLoggedIn;
                }, true);

                scope.changeFormMode = function () {
                    scope.isRegister ^= true;
                    scope.isInvalidData = undefined;
                };

                scope.login = function () {
                    var email = scope.user.email;
                    var password = scope.user.password;

                    try {
                        var userName = $rootScope.backendService.userLogin(email, password);
                        setSession(email, userName);
                        resetFormData();
                    } catch (e) {
                        scope.isInvalidData = treatException(e);
                    }
                };

                scope.register = function () {
                    var userName = scope.user.userName;
                    var email = scope.user.email;
                    var password = scope.user.password;
                    var password2 = scope.user.password2;

                    try {
                        $rootScope.backendService.userRegister(email, userName, password, password2);
                        scope.isRegister = false;
                        setSession(email, userName);
                        resetFormData();
                    } catch (e) {
                        scope.isInvalidData = treatException(e);
                    }
                };

                function setSession(email, userName) {
                    $rootScope.isLoggedIn = true;
                    $rootScope.currentUser = {
                        email: email,
                        userName: userName
                    };
                }

                function resetFormData() {
                    scope.user = {
                        userName: '',
                        email: '',
                        password: '',
                        password2: ''
                    };
                    scope.isInvalidData = undefined;
                }

                function treatException(e) {
                    if (e.toString().indexOf('UserNotFoundException') !== -1) {
                        return 'EXCEPTION_USER_NOT_FOUND';
                    } else if (e.toString().indexOf('InvalidDetailsException') !== -1) {
                        return 'EXCEPTION_INVALID_DETAILS';
                    } else if (e.toString().indexOf('AlreadyExistingUserException') !== -1) {
                        return 'EXCEPTION_ALREADY_EXISTING_USER';
                    }
                }

            }
        };
    }

}());