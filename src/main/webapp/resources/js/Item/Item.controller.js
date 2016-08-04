'use strict';
(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());

function itemController($scope, $routeParams, $http){

    // Grab Item ID from URL
    var itemId = $routeParams.itemId;
    var itemCollectionUrl = "/item/";

    $scope.disqusConfig = {
        disqus_shortname: 'guarded-journey-11719-herokuapp-com',
        disqus_identifier: 'auction-item' + itemId,
        disqus_url: 'localhost:8080/app/item/' + itemId
    };

    $scope.noWrapSlides = false;
    $scope.active = 0;

    // Get Item data for itemId
    $http.get(itemCollectionUrl + itemId).success(function(data){
        $scope.item = data;
        $scope.slides = data.itemPictures;
    });
}

