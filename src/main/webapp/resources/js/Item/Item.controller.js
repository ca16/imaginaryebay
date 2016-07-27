'use strict';

(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());

function itemController($scope, $routeParams, ItemService){

    // Grab Item ID from URL
    var itemId = $routeParams.itemId;

    $scope.noWrapSlides = false;
    $scope.active = 0;

    // Get Item data for itemId
    ItemService.getItem(itemId).success(function(data) {
        $scope.item = data;
        $scope.slides = data.itemPictures;
        console.log($scope.slides);
    });
}
