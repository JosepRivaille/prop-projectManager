(function () {
    'use strict';

    /**
     * @ngdoc directive
     * @name project.directives.directive:StarRating
     *
     * @description
     *
     */
    angular
        .module('project')
        .controller('StarRatingCtrl', StarRatingCtrl)
        .directive('starRating', starRating);

    function StarRatingCtrl() {
        var vm = this;
        vm.ctrlName = 'StarRatingCtrl';
    }
    
    function starRating() {
        return {
            restrict: 'EA',
            templateUrl: 'directives/star-rating/star-rating.tpl.html',
            scope: {
                ratingValue: '=ngModel',
                readOnly: '=?'
            },
            link: function (scope) {
                function updateStars() {
                    scope.stars = [];
                    for (var i = 0; i < 5; i++) {
                        scope.stars.push({
                            filled: i < scope.ratingValue
                        });
                    }
                }
                scope.toggle = function (index) {
                    if (angular.isUndefined(scope.readOnly) || scope.readOnly === false) {
                        scope.ratingValue = index + 1;
                    }
                };
                scope.$watch('ratingValue', function (oldValue, newValue) {
                    if (newValue) {
                        updateStars();
                    }
                });
            }
        };
    }

}());