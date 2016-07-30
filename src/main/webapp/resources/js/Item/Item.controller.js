'use strict';

(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());

function itemController($scope, $routeParams, $http){

    // Grab Item ID from URL
    var itemId = $routeParams.itemId;
    var itemCollectionUrl = "/item/";

    $scope.noWrapSlides = false;
    $scope.active = 0;

    // Get Item data for itemId
    $http.get(itemCollectionUrl + itemId).success(function(data){
        $scope.item = data;
        $scope.slides = data.itemPictures;
    });
}

