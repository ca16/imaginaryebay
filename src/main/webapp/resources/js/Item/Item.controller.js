(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());


function itemController($scope, $http, ItemService){
        ItemService.getItem(1).success(function(data) {
                $scope.item = data;
        });
}
