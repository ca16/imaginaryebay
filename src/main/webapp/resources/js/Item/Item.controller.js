(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());

function itemController($scope, $routeParams, ItemService){

        // Grab Item ID from URL
        var itemId = $routeParams.itemId;

        // Get Item data for itemId
        ItemService.getItem(itemId).success(function(data) {
                $scope.item = data;
        });
}
