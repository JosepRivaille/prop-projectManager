(function () {
    'use strict';

    /**
     * @ngdoc component
     * @name project.components.directive:StarRating
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
                onRatingSelect: '&?',
                readonly: '=?'
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
                    if (scope.readonly == undefined || scope.readonly === false) {
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