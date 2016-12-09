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
                readOnly: '=?',
                documentTitle: '=?',
                documentAuthor: '=?',

            },
            link: function (scope, $rootScope) {
                function updateStars() {
                    scope.stars = [];
                   /* alert("STAR RATING: " + scope.ratingValue);
                    alert("STAR RATING: " + scope.documentTitle);
                    alert("STAR RATING: " + scope.documentAuthor);*/
                   alert(scope.documentTitle + " ==> " + scope.ratingValue);
                    for (var i = 0; i < 5; i++) {
                        scope.stars.push({
                            filled: i < scope.ratingValue
                        });
                    }
                    alert(JSON.stringify(scope.stars));
                }
                scope.toggle = function (index) {
                    if (angular.isUndefined(scope.readOnly) || scope.readOnly === false) {
                        scope.ratingValue = index + 1;
                    }
                };
                scope.$watch('ratingValue', function (oldValue, newValue) {
                    if (newValue) {
                        //$rootScope.backendService.rateDocument(documentTitle, documentAuthor);
                        updateStars();
                    }
                });
            }
        };
    }

}());